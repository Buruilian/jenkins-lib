@Library("mylib@main") _
import org.devops.*

def checkout = new Checkout()
def build = new Build()
def unittest = new UnitTest()

//env.buildType = "${JOB_NAME}".split("-")[1]

pipeline {
    agent {
        label "build"
    }

    options {
        skipDefaultCheckout true
    }

    stages {
        stage("Checkout") {
            steps {
                script {
                    println("GetCode")
                    checkout.GetCode("${env.srcUrl}", "${env.branchName}")
                }
            }
        }

        stage("Build") {
            steps {
                script {
                    println("Build")
                    build.CodeBuild("${env.buildType}")
                }
            }
        }

        /*stage("Test") {
            steps {
                script {
                    println("Test")
                    build.CodeTest("${env.buildType}")
                }
            }
        }*/

        stage("CodeScan") {
            steps {
                script {
                    println("Code Scan")
                    withCredentials([usernamePassword(credentialsId: 'f7d0c5f7-2803-4d86-9e5e-d0160f474bf0', passwordVariable: 'SONAR-PASSWORD', usernameVariable: 'SONAR-USER')]) {
                        sh """/usr/local/sonar-scanner/bin/sonar-scanner \
                            -Dsonar.login=${SONAR-USER} \
                            -Dsonar.password=${SONAR-PASSWORD} \
                            -Dsonar.projectVersion=${env.branchName}
                        """
                    }
                }
            }
        }
    }
}
