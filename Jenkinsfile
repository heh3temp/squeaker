pipeline {
    agent any
    stages {
        stage('Hello World!') {
            steps {
                sh 'echo Hello World'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
    }
}
