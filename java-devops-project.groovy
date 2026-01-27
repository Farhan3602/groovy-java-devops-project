pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Fetch the repository using SSH credentials
                git branch: 'main', url: 'https://github.com/Farhan3602/Java-DevOps-Project'
                //sh "scp -r * ubuntu@65.0.170.92:/home/ubuntu"
                sh 'scp -o StrictHostKeyChecking=no -r * ubuntu@13.201.178.164:/home/ubuntu'
            }
        }
        stage('creating a docker image') {
            steps {
                sshagent(['docker-server']) {
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.201.178.164 "docker build -t farhancool/krish:$BUILD_ID ."'
                    sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.201.178.164 "docker tag  farhancool/krish:$BUILD_ID farhancool:latest "'
                    }
                }
        }
        stage('Push Image to DockerHub'){
            steps {
            withCredentials([string(credentialsId: 'farhancool', variable: 'DockerPass')]) {
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.201.178.164 "docker login -u farhancool -p ${DockerPass}"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.201.178.164 "docker image push farhancool/krish:$BUILD_ID"'
                sh 'ssh -o StrictHostKeyChecking=no ubuntu@13.201.178.164 "docker image push farhancool/krish:latest"'
                }
            }
        }
    }
}