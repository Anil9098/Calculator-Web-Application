properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {
    
	//sh "ssh -i $key ubuntu@13.233.100.250"
	def image

    stage('Run SSH Command on EC2') {
        // Running commands on remote EC2 instance
        sh '''
        //#!/bin/bash

        # SSH to EC2 and run commands
        //sudo ls -la /root/.ssh/
        //sudo ssh -i /root/.ssh/id_rsa ubuntu@13.233.100.250
        ./deploy_ssh.sh
        '''
    }

	stage('Code Clone') {
          // git clone
	  echo "code cloning"
          checkout([$class: 'GitSCM',
	    branches: [[name: '*/master']],  
            userRemoteConfigs: [[
                url: "https://github.com/Anil9098/Calculator-Web-Application.git",
		credentialsId: "gitCredentialsId"		
            ]]
         ])
       }
	
	stage('Build Image') {
            echo "Building Docker image"
            image = docker.build("${DOCKER_USERNAME}/web_application:${DOCKER_TAG}")
        }
	
        // login to docker hub
        stage('Login to Docker Hub') {
            withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker Hub"
            }
       }

        stage('push to docker hub') {
	    withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Pushing image to docker hub"
		image.push()
            }
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}








