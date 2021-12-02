pipeline {
    agent {
        docker { image "openjdk:17"}
    }
    stages {
        stage('Check java version') {
            sh 'java -version'
        }
        stage('Compile') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean classes'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}
