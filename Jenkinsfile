@Library("mylib@main") _
import org.devops.*

def checkout = new Checkout()
def build = new Build()
def unittest = new UnitTest()
def sonar = new Sonar()

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
                    sonar.CodeScan("${env.branchName"})
                }
            }
        }
    }
}
