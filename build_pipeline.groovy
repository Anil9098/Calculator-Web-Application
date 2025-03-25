properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Docker image tag')
    ])
])

node {
    try {

	def dockerTag = params.DOCKER_TAG ?: 'latest'
        // Stage 1: Build Docker image
        stage('Build') {
            echo "Building Docker image"
            sh 'docker build -t $DOCKER_USERNAME/web_application:$DOCKER_TAG .'
        }

        // Stage 2: Login to Docker Hub
        stage('Login to Docker Hub') {
            echo "Logging into Docker Hub"
            // Secure login using password from environment variables
            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
        }

        // Stage 3: Push the image to Docker Hub
        stage('Push to Docker Hub') {
            echo "Pushing Docker image to Docker Hub"
            // Push the image to Docker Hub
            sh 'docker push $DOCKER_USERNAME/web_application:$DOCKER_TAG'
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed.'
    }
}





