package org.devops

//获取文件内容
def GetRepoFile(projectId, filePath, branchName) {
	//GET /projects/:id/repository/files/:file_path/raw
	apiUrl = "/projects/${projectId}/repository/files/${filePath}/raw?ref=${branchName}"
	response = HttpReq('GET', apiUrl)
	return response.content
}

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
            http://172.31.0.10/api/v4/projects?search=${projectName} \
            --header 'PRIVATE-TOKEN: j53sXHNtGH3s7GGsT-CD'
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

def HttpReq(method, apiUrl) {
    response = sh  returnStdout: true, 
    script: """ 
        curl --location --request ${method} \
        http://172.31.0.10/api/v4${apiUrl} \
        --header 'PRIVATE-TOKEN: j53sXHNtGH3s7GGsT-CD'
    """
    println(response.class)
    return response
}


def GetBranchCommitID(projectID, branchName){
    apiUrl = "projects/${projectID}/repository/branches/${branchName}"
    response = HttpReq("GET", apiUrl)
    response = readJSON text: response - "\n"
    return response.commit.short_id
}
