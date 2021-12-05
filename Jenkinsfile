pipeline {
    agent any
    tools {
        jdk 'java-17-openjdk'
    }
    stages {
        stage('Compile') {
            steps {
                sh './gradlew clean classes'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh './gradlew sonarqube -Dsonar.login=${SONAR_AUTH_TOKEN}'
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}
