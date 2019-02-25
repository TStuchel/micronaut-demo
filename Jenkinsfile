pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/TStuchel/micronaut-demo'
                sh 'gradle build'
            }
        }
    }
}