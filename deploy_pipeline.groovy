properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {
        // Stage 1: Login to Docker Hub
        stage('Login to Docker Hub') {
            withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker Hub"
            }
        }


        // Stage 2: Pull Image
        stage('Pull Image') {
            echo "Pulling Docker image..."
            def image = ("${DOCKER_USERNAME}/web_application:$DOCKER_TAG")
	    image.pull()
        }


        // Stage 3: Deploy
        stage('Deploy') {
            echo "Deploying the application..."
            sh 'docker rm -f web-app || true'  
            sh 'docker run -d -p 5000:5000 --name web-app $DOCKER_USERNAME/web_application:$DOCKER_TAG'  
            sh 'docker ps'  
            sh 'pwd'  
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e 
    } finally {
        echo 'Pipeline completed' 
    }
}

	
