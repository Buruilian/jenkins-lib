package org.devops

、
def GetCode(branchName, srcUrl) {
    checkout(
        [
            $class: 'GitSCM', 
            branches: [[name: branchName]], 
            extensions: [], 
            userRemoteConfigs: [[credentialsId: 'f32d37bd-de73-43e3-b7ca-7fcd707de1f8', url: srcUrl]]
        ]
   )
}
