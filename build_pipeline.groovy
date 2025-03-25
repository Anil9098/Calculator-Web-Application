properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])

//def dockertag = params.DOCKER_TAG ?: 'latest'

node {
    try {

	//def dockertag = params.DOCKER_TAG ?: 'latest'
	echo "docker image tag: $DOCKER_TAG"

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





