pipeline {
    agent any

    stages {
        stage('Build') {
            steps { 
                sh 'docker build -t web_application .'
            }
        }

	stage('Test') {
	    steps {
		script {
                    def imageExists = sh(script: "docker images -q myapp:latest", returnStdout: true).trim()
                    if (!imageExists) {
                        error "Docker image 'web_application:latest' was not built successfully"
                    } else {
                        echo "Docker image 'myapp:latest' built successfully"
		    }
		}
	    }
	}

        stage('Deploy') {
            steps {
                sh 'docker rm -f web-app || true'
                sh 'docker run -d -p 5000:5000 --name web-app web_application'
                sh 'docker ps'
                sh 'pwd'
            }
        }
    }
}
