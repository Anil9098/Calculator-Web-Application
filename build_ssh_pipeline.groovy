node {
    try {
        stage('SSH on EC2') {
            sh "sudo ./root/ncs/Anil/web_app_deploy_script/bash/deploy_ssh.sh"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}











                                                                                                                                                                                                                                                                                                                                                                                                    












