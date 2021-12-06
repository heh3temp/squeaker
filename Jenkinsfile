pipeline {
    environment {
        def version = sh (
            script: './gradlew -q printVersion',
            returnStdout: true
        ).trim()
    }

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
                nexusArtifactUploader(
                    credentialsId: 'nexus-admin',
                    groupId: 'com.hamsterbusters',
                    nexusUrl: 'localhost:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'maven-nexus-repo',
                    version: "${version}",
                    artifacts: [
                        [
                            artifactId: 'squeaker',
                            classifier: '',
                            file: "build/libs/squeaker-${version}.jar",
                            type: 'jar'
                        ]
                    ]
                )
            }
        }
    }
}
