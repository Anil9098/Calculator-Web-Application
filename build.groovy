node {
    try {
        stage('Run Script') {
            sh "sudo ./deploy_ssh.sh"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}

