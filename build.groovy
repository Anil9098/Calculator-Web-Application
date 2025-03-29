node {
    try {
        stage('Run Script on EC2') {

            // Ensure all commands run inside the SSH session on the EC2 instance
            sh '
                ./deploy_ssh.sh
            '
        }
        
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}
