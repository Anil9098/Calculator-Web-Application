pipeline {
    agent any

    stages {
        stage('Build') {
            steps { 
                sh 'docker build -t web_application .'
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
