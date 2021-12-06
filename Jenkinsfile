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
            environment {
                script {
                    def appVersion = sh (
                        script: "gradle properties | grep 'version' | awk '{print \$2}'",
                        returnStdout: true
                    ).trim()
                }
            }

            steps {
                sh "echo ${appVersion}"
                nexusArtifactUploader(
                    credentialsId: 'nexus-admin',
                    groupId: 'com.hamsterbusters',
                    nexusUrl: 'localhost:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'maven-nexus-repo',
                    version: "${appVersion}",
                    artifacts: [
                        [
                            artifactId: 'squeaker',
                            classifier: '',
                            file: "build/libs/squeaker-${appVersion}.jar",
                            type: 'jar'
                        ]
                    ]
                )
            }
        }
    }
}
