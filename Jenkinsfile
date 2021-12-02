pipeline {
    agent any
    tools {
        jdk 'java-17-openjdk'
    }
    stages {
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
