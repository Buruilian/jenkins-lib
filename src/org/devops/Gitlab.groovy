package org.devops

//获取CommitID
def GetCommitID(){
    ID = sh returnStdout: true, script:"git rev-parse HEAD"
    ID = ID - "\n"
    return ID[0..7]
}

//获取ProjectID
// fork
// namespace 
// usera/devops-service-app
// userb/devops-service-app 
def GetProjectID(projectName, groupName){
    response = sh  returnStdout: true, 
        script: """ 
            curl --location --request GET \
            http://172.31.1.10/api/v4/projects?search=${projectName} \
            --header 'PRIVATE-TOKEN: N-kxsLC3KMZkfX8NJiLR' \
            --header 'Authorization: Basic cm9vdDoxMjM0NTY3OA=='
        """
    response = readJSON text: response
    if (response != []){
        for (p in response) {
            if (p["namespace"]["name"] == groupName){
                return response[0]["id"]
            }
        }
    }
}
