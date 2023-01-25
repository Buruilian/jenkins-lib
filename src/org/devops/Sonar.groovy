package org.devops

def CodeScan(branchName, commitID, projectID) {
    withCredentials([usernamePassword(credentialsId: 'f7d0c5f7-2803-4d86-9e5e-d0160f474bf0', 
                    passwordVariable: 'SONAR_PASSWORD', 
                    usernameVariable: 'SONAR_USER')]) {
        sh """/usr/local/sonar-scanner/bin/sonar-scanner \
            -Dsonar.login=${SONAR_USER} \
            -Dsonar.password=${SONAR_PASSWORD} \
            -Dsonar.projectVersion=${branchName} \
            -Dsonar.branch.name=${branchName} \
            -Dsonar.gitlab.commit_sha=${commitID} \
            -Dsonar.gitlab.ref_name=${branchName} \
            -Dsonar.gitlab.project_id=${projectID} \
            -Dsonar.dynamicAnalysis=reuseReports \
            -Dsonar.gitlab.failure_notification_mode=commit-status \
            -Dsonar.gitlab.url=http://172.31.1.10 \
            -Dsonar.gitlab.user_token=j53sXHNtGH3s7GGsT-CD \
            -Dsonar.gitlab.api_version=v4

           """
       }
}


def Init(projectName, lang, profileName){
    result = ProjectSearch(projectName)
    println(result)
    if (result == false){
        CreateProject(projectName)
    }

    UpdateQualityProfiles(lang, projectName, profileName)
}


// 更新质量配置
def UpdateQualityProfiles(lang, projectName, profileName){
    apiUrl = "qualityprofiles/add_project?language=${lang}&project=${projectName}&qualityProfile=${profileName}"
    response = SonarRequest(apiUrl,"POST")
    
    if (response.errors != true){
        println("ERROR: UpdateQualityProfiles ${response.errors}...")
        return false
    } else {
        println("SUCCESS: UpdateQualityProfiles ${lang} > ${projectName} > ${profileName}" )
        return true
    }
}

// 创建项目
def CreateProject(projectName){
    apiUrl = "projects/create?name=${projectName}&project=${projectName}"
    response = SonarRequest(apiUrl,"POST")
    try{
        if (response.project.key == projectName ) {
            println("Project Create success!...")
            return true
        }
    }catch(e){
        println(response.errors)
        return false
    }
}

// 查找项目
def ProjectSearch(projectName){
    apiUrl = "projects/search?projects=${projectName}"
    response = SonarRequest(apiUrl,"GET")
    println(response)

    if (response.paging.total == 0){
        println("Project not found!.....")
        return false
    } 
    return true
}

def SonarRequest(apiUrl,method){
    withCredentials([string(credentialsId: "ea4a8bbd-ee0e-47b5-88dd-25f8572e3b15", variable: 'SONAR_TOKEN')]) {
        sonarApi = "http://172.31.1.10:9000/api"
        response = sh  returnStdout: true, 
            script: """
            curl --location \
                 --request ${method} \
                 "${sonarApi}/${apiUrl}" \
                 --header "Authorization: Basic ${SONAR_TOKEN}"
            """
        try {
            response = readJSON text: """ ${response - "\n"} """
        } catch(e){
            response = readJSON text: """{"errors" : true}"""
        }
        return response
    }
}

