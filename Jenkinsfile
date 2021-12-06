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
            when {
                changeRequest target: "develop"
            }
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
