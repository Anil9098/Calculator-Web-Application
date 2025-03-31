node {
    try {
        stage('SSH on EC2') {
            //sh "cd /home/ncs/Anil/web_app_deploy_script/bash"
            //sh "cd /home/ncs/Anil/web_app_deploy_script/bash"
            //sh"pwd"
            //echo "path fixed"
            sh "public_ip=$(aws ec2 describe-instances --query "Reservations[].Instances[].PublicIpAddress" --output text)"
            sh '''for ip in $public_ip; do
                    echo "Processing IP: $ip"
                    ssh -i "/home/ncs/Downloads/jenkinsnodekey.pem" ubuntu@$ip <<EOF
                    rm -rf anil_practice
                    git clone https://github.com/Anil9098/anil_practice.git
                    cd anil_practice/bash
                    ./example_deployment.sh
                EOF
                done
            '''           
            //sh "sudo ./deploy_ssh.sh"
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}












                                                                                                                                                                                                                                                                                                                                                                                                    












