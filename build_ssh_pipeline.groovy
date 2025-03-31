node {
    try {
        stage('Run Script') {

            sh '''
                #!/bin/bash

                #sudo ssh -i /root/.ssh/id_rsa ubuntu@13.233.100.250
                
                sudo ssh -i "/home/ncs/Downloads/jenkinsnodekey.pem" ubuntu@13.234.67.6 <<EOF
                
                # Commands to run inside EC2

                rm -rf anil_practice

                git clone https://github.com/Anil9098/anil_practice.git

                cd anil_practice/bash

                ./example_deployment.sh 

                EOF
            '''
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}
























