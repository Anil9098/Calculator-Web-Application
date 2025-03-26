properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {

	//def dockertag = params.DOCKER_TAG ?: 'latest'
	//echo "docker image tag: $DOCKER_TAG"

        // Stage 1: Build Docker image
	
       // stage('Code Clone') {
         //   echo "cloning git repository"
	 //   withCredentials([gitUsernamePassword(credentialsId: 'gitCredentialsId',url: "https://github.com/Anil9098/Calculator-Web-Application.git")])
	    
	stage('Checkout') {
          // Checkout the private repository using Git with credentials
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
            def image = docker.build("${DOCKER_USERNAME}/web_application:${DOCKER_TAG}")
        }
	
        // Stage 2: Login to Docker Hub
        stage('Login to Docker Hub') {
            withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker Hub"
            }
        }

       // Stage 3: Push the image to Docker Hub
       // stage('Push to Docker Hub') {

	   // docker.withRegistry("[credentialsId: 'docker_hub_credentials']") {
           //    image.push()
         //  echo "Pushing Docker image to Docker Hub"
          // ("${DOCKER_USERNAME}/web_application:${DOCKER_TAG}").push()
         //   } 
       // }


	stage('Push to Docker Hub') {
            withCredentials([usernamePassword(credentialsId: 'docker_hub_credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                docker.withRegistry('https://index.docker.io/v1/', 'docker_hub_credentials') {
                    echo "Pushing Docker image to Docker Hub"
                    image.push("${params.DOCKER_TAG}")
                }
            }
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}





