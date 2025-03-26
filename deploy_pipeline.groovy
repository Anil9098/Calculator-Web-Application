properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {
	def image
	def command
        // Stage 1: Login to Docker Hub
        stage('Login to Docker Hub') {
            withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker"
            }
        }
       

        //Stage 2: Pull Image
        stage('Pull Image') {
         //   echo "Pulling Docker image..."
	    withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker Hub"
                image = docker.image("${DOCKER_USERNAME}/web_application:$DOCKER_TAG")
                image.pull()
            }
	}

        // Stage 3: Deploy
        stage('Deploy') {
            echo "Deploying the application"
            command = "docker rm -f web-app || true"
	    command.execute()  
	   // process.waitFor()
            sh 'docker run -d -p 5000:5000 --name web-app $DOCKER_USERNAME/web_application:$DOCKER_TAG'  
            sh 'docker ps'  
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e 
    } finally {
        echo 'Pipeline completed' 
    }
}

	
