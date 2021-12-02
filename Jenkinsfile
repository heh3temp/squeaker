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
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }
    }
}
