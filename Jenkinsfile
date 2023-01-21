@Library("mylib@main") _
import org.devops.*

def checkout = new Checkout()
def build = new Build()
def unittest = new UnitTest()
def sonar = new Sonar()
def gitcli = new Gitlab()

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
            when {
		        environment name: 'skipSonar', value: 'false'
	        }     
            steps {
                script {
                    //代码扫描
                    println("Code Scan")
                    profileName = "${JOB_NAME}".split("-")[0]
                    sonar.Init("${JOB_NAME}", "java", profileName )
                    //commit-status
                    commitID = gitcli.GetCommitID()
                    groupName = profileName
                    projectID = gitcli.GetProjectID("${JOB_NAME}", groupName)

                    sonar.CodeScan("${env.branchName}", commitID, projectID)   
                }
            }
        }
    }
}
