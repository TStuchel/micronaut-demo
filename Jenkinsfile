pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/TStuchel/micronaut-demo'
                sh 'gradle build'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}