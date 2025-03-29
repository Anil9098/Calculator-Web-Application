properties([
    parameters([
        string(name: 'DOCKER_TAG', defaultValue: 'new', description: 'Image Tag')
    ])
])

node {
    try {
        def image

        // SSH Key Path
        def sshKeyPath = "/root/.ssh/id_rsa"  // Modify if needed

        // EC2 Instance Information
        def ec2User = "ubuntu"
        def ec2Host = "13.233.100.250" // Your EC2 public IP

        // Remote SSH Command Template
        def remoteCommands = { command ->
            return """
                ssh -i ${sshKeyPath} ${ec2User}@${ec2Host} << EOF
                    # Remote commands to run on EC2
                    ${command}
                EOF
            """
        }

        stage('Run SSH Command on EC2') {
            echo "Running SSH commands on EC2 instance"
            sh """
                # Running a basic SSH command to test connectivity
                ssh -i ${sshKeyPath} ${ec2User}@${ec2Host} 'echo "Connected to EC2 successfully!"'
            """
        }

        stage('Code Clone') {
            echo "Cloning code on EC2"

            // Run Git clone on EC2
            sh remoteCommands("""
                echo 'Cloning repository on EC2...'
                git clone https://github.com/Anil9098/Calculator-Web-Application.git
            """)
        }

        stage('Build Image') {
            echo "Building Docker image on EC2"

            // Run Docker build on EC2
            sh remoteCommands("""
                echo 'Building Docker image on EC2...'
                cd Calculator-Web-Application  # Assuming the project folder name is 'Calculator-Web-Application'
                docker build -t ${DOCKER_USERNAME}/web_application:${DOCKER_TAG} .
            """)
        }

        stage('Login to Docker Hub') {
            echo "Logging into Docker Hub on EC2"

            // Run Docker Hub login on EC2
            sh remoteCommands("""
                echo 'Logging into Docker Hub on EC2...'
                echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
            """)
        }

        stage('Push to Docker Hub') {
            echo "Pushing Docker image to Docker Hub on EC2"

            // Push Docker image to Docker Hub from EC2
            sh remoteCommands("""
                echo 'Pushing image to Docker Hub on EC2...'
                docker push ${DOCKER_USERNAME}/web_application:${DOCKER_TAG}
            """)
        }

    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        throw e
    } finally {
        echo 'Pipeline completed'
    }
}
