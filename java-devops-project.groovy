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
                    ec2-ubuntu@13.200.16.136 \
                    "ls -la /home/ubuntu@ip-172-31-20-250/"
                '''
            }
        }
    }
    }
}