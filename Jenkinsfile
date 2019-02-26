pipeline {
    agent any

    stages {
        stage('Build / Unit Test') {
            steps {
                git 'https://github.com/TStuchel/micronaut-demo'
                sh 'gradle clean build'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}