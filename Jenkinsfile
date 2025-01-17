def APP_VERSION

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
        stage('Upload artifacts') {
            steps {
                script {
                    APP_VERSION = sh (
                        script: "./gradlew properties | grep 'version' | awk '{print \$2}'",
                        returnStdout: true
                    ).trim()
                }
                nexusArtifactUploader(
                    credentialsId: 'nexus-admin',
                    groupId: 'com.hamsterbusters',
                    nexusUrl: 'localhost:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'maven-nexus-repo',
                    version: "${APP_VERSION}",
                    artifacts: [
                        [
                            artifactId: 'squeaker',
                            classifier: '',
                            file: "build/libs/squeaker-${APP_VERSION}.jar",
                            type: 'jar'
                        ]
                    ]
                )
            }
        }
    }
}
