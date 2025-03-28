properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {
    
	sh "ssh -i $path ubuntu@65.0.170.83"
        def image	    
	stage('Code Clone') {
          // git clone
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





