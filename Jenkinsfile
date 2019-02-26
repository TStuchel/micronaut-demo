pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/TStuchel/micronaut-demo'
            }
        }
        stage('Build/Test') {
            steps {
                sh 'gradle clean build'
                junit 'build/test-results/test/*.xml'
            }
        }
        stage('SonarQube') {
            withSonarQubeEnv('localhost:9000') {
                sh './gradlew --info sonarqube'
            }
        }
    }
}