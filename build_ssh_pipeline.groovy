node {
    try {
        stage('Run Script') {

            sh '''
                
                sudo ssh -i /root/.ssh/id_rsa ubuntu@13.233.100.250 <<- 'EOF'
                # Commands to run inside EC2
                
                git clone https://github.com/Anil9098/Calculator-Web-Application.git
                
                cd Calculator-Web-Application || { echo "Failed to enter directory"; exit 1; }
                                
                ./example_deployment.sh

            '''
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}



