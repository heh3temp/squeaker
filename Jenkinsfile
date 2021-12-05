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
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Upload artifacts') {
            steps {
                nexusPublisher nexusInstanceId: 'nexus1', nexusRepositoryId: 'maven-nexus-repo', packages: []
            }
        }
    }
}
