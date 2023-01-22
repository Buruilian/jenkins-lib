@Library("mylib@main") _
import org.devops.*

def checkout = new Checkout()
def build = new Build()
def unittest = new UnitTest()
def sonar = new Sonar()
def gitcli = new Gitlab()
def artifact = new Artifact()

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

                    env.buName = "${JOB_NAME}".split("-")[0]
                    env.serviceName = "${JOB_NAME}".split("_")[0]
                    env.commitID = gitcli.GetCommitID()

                    currentBuild.description = """
                    srcUrl: ${env.srcUrl} \n
                    branchName: ${env.branchName} \n
                    """
                    currentBuild.displayName = "${env.commitId}"
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
                    sonar.Init("${env.serviceName}", "java", profileName )

                    //commit-status
                    groupName = profileName
                    projectID = gitcli.GetProjectID("${env.serviceName}", groupName)
                    sonar.CodeScan("${env.branchName}", env.commitID, projectID)   
                }
            }
        }

        stage("PushArtifact"){
            steps{
                script{
                    // Dir /buName/serviceName/version/serviceName-version.xxx
                    version = "${env.branchName}-${env.commitID}"

                    // 重命名制品
                    JarName = sh returnStdout: true, script: """ls target|grep -E "jar\$" """
                    fileName = JarName -"\n"
                    fileType = fileName.split('\\.')[-1]
                    newFileName = "${serviceName}-${version}.${fileType}"
                    sh "cd target; mv ${fileName} ${newFileName}"

                    // 上传制品
                    artifact.PushArtifact("${env.buName}/${env.serviceName}/${version}", "target", "${newFileName}")
                }
            }
        }
    }
}

