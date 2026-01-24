pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/Farhan3602/Java-DevOps-Project'
                //sh "scp -r * ubuntu@65.0.170.92:/home/ubuntu"
                sh 'scp -o StrictHostKeyChecking=no -r transfer ubuntu@65.0.170.92:/home/ubuntu'
            }
        }
        stage('List Remote Files') {
            steps {
                sshagent(['docker-server']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@65.0.170.92 "docker build -t farhancool/pyapp:v1 ."'
                    }
                }
        }
    }
}