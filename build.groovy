node {
    try {
        stage('Run Script') {

            sh '''
                sudo ssh -i /root/.ssh/id_rsa ubuntu@13.233.100.250 
                # Commands to run on the EC2 instance
                echo "Running script on EC2 instance"
                pwd
                git clone https://github.com/Anil9098/Calculator-Web-Application.git
            '''
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}

