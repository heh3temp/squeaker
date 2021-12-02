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
                gradlew('clean', 'build')
            }
        }
    }
}
