def version_value = sh(returnStdout: true, script: "cat build.gradle | grep -o 'version = [^,]*'").trim()
def version = version_value.split(/=/)[1]

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
                nexusArtifactUploader(
                    credentialsId: 'nexus-admin',
                    groupId: 'com.hamsterbusters',
                    nexusUrl: 'localhost:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'maven-nexus-repo',
                    version: '$version',
                    artifacts: [
                        [
                            artifactId: 'squeaker',
                            classifier: '',
                            file: 'build/libs/squeaker-$version.jar',
                            type: 'jar'
                        ]
                    ]
                )
            }
        }
    }
}
