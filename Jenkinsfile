pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Fabo2303/pipeline.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}
