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
	
      //  stage('Code Clone') {
	//    echo "cloning git repository"
	 //   withCredentials([gitUsernamePassword(credentialsId: 'gitCredentialsId',url: "https://github.com/Anil9098/Calculator-Web-Application.git")])
	

	stage('Build Image') {
            echo "Building Docker image"
            sh 'docker build -t $DOCKER_USERNAME/web_application:$DOCKER_TAG .'
        }
	
        // Stage 2: Login to Docker Hub
        stage('Login to Docker Hub') {
            withDockerRegistry([credentialsId: 'docker_hub_credentials']) {
                echo "Successfully logged into Docker Hub"
            }
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
        echo 'Pipeline completed'
    }
}





