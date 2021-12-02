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
                sh 'java -version'
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }
    }
}
