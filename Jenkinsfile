pipeline {
    agent any
    tools{
    jdk 'Java17'
        maven 'Maven3'

    }

    environment {
    PATH = "/usr/local/bin:${env.PATH}"
        DOCKERHUB_CREDENTIALS_ID = 'docker_hub'
        DOCKER_IMAGE = 'phongle7de/javafx_with_mariadb'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Setup Maven') {
            steps {
                script {
                    def mvnHome = tool name: 'Maven3', type: 'maven'
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                    echo "Maven setup completed. PATH: ${env.PATH}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/PhongLe7de/week7_inclass.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "/usr/local/bin/docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }


//         stage('Push Docker Image to Docker Hub') {
//             steps {
//                 script {
//                     docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS_ID) {
//                         docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
//                     }
//                 }
//             }
//         }
stage('Push Docker Image to Docker Hub') {
    steps {
        withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
                                          usernameVariable: 'DOCKER_HUB_USERNAME',
                                          passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
            sh """
                echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin
                docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
            """
        }
    }
}
    }

  post {
    always {
        junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)
        jacoco(execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', inclusionPattern: '**/*.class', exclusionPattern: '')
    }
}


}

