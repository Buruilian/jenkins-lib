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
            --header 'PRIVATE-TOKEN: j53sXHNtGH3s7GGsT-CD' \
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

def HttpReq(method, apiUrl){
    response = sh  returnStdout: true, 
    script: """ 
        curl --location --request ${method} \
        http://172.31.1.10/api/v4/${apiUrl} \
        --header 'PRIVATE-TOKEN: j53sXHNtGH3s7GGsT-CD' \
    """
    response = readJSON text: response - "\n"
    return response
}


def GetBranchCommitID(projectID, branchName){
    apiUrl = "projects/${projectID}/repository/branches/${branchName}"
    response = HttpReq("GET", apiUrl)
    return response.commit.short_id
}
