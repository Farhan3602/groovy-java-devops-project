pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/Farhan3602/Java-DevOps-Project'
            }
        }
    }
    stage('List Remote Files') {
            steps {
                sshagent(['docker-server']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.200.16.136 "ls -la ~/"'
                    }
                }
        }
}