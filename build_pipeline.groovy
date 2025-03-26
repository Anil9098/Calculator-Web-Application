properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])


node {
    try {

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
		echo "pushing docker image"
		image.push()
            }
        }

       // stage('push to docker hub') {

	  // docker.withRegistry("[credentialsId: 'docker_hub_credentials']") {
         //      image.push()
	//}


//	stage('Push to Docker Hub') {
//            withCredentials([usernamePassword(credentialsId: 'docker_hub_credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
//                docker.withRegistry('https://index.docker.io/v1/', 'docker_hub_credentials') {
//                    echo "Pushing Docker image to Docker Hub"
//                    image.push()
//                }
//            }
//        }


    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}





