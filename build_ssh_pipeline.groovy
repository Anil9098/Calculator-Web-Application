node {
    try {
        stage('SSH on EC2') {
            //sh "cd /home/ncs/Anil/web_app_deploy_script/bash"
            //sh "cd /home/ncs/Anil/web_app_deploy_script/bash"
            //sh"pwd"
            //echo "path fixed"
            sh "sudo ./deploy_ssh.sh"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}












                                                                                                                                                                                                                                                                                                                                                                                                    












