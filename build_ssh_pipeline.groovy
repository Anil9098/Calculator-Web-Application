node {
    try {
        stage('SSH on EC2') {
            sh "./deploy_ssh.sh"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}











                                                                                                                                                                                                                                                                                                                                                                                                    












