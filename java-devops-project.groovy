pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/Farhan3602/Java-DevOps-Project'
            }
        }
        stage('List files on EC2 home directory') {
    steps {
        sshagent(credentials: ['your-ec2-ssh-credential-id']) {
            sh '''
                ssh -o StrictHostKeyChecking=no \
                    -o UserKnownHostsFile=/dev/null \
                    ec2-user@your-ec2-public-ip \
                    "ls -la /home/ec2-user/"
                '''
            }
        }
    }
    }
}