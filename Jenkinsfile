pipeline {
    agent {
        docker {
            image 'gradle'
        }
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/TStuchel/micronaut-demo'
                sh 'gradle build'
            }
        }
    }
}