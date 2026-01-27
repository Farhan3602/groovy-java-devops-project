pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/Farhan3602/Java-DevOps-Project'
                //sh "scp -r * ubuntu@65.0.170.92:/home/ubuntu"
                sh 'scp -o StrictHostKeyChecking=no -r * ubuntu@15.206.164.15:/home/ubuntu'
            }
        }
        stage('List Remote Files') {
            steps {
                sshagent(['docker-server']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@15.206.164.15 "docker build -t farhancool/pyapp:v1 ."'
                    }
                }
        }
        stage{'Push Image to DockerHub'}{
            withCredentials([string(credentialsId: 'farhancool', variable: 'DockerPass')]) {
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@15.206.164.15 "docker login -u farhancool -p ${DockerPass}"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@15.206.164.15 "docker image push farhancool/krish:v1.$BUILD_ID"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@15.206.164.15 "docker image push farhancool/krish:latest"'
        }
    }
    }
}