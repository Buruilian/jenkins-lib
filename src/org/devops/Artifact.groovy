package org.devops

def PushArtifact(targetDir, filePath){
    // 上传制品
    sh """
        curl -X POST "http://172.31.1.10:8081/service/rest/v1/components?repository=devops4-local" \
        -H "accept: application/json" \
        -H "Content-Type: multipart/form-data" \
        -F "raw.directory=${targetDir}" \
        -F "raw.asset1=@${filePath};type=application/java-archive" \
        -F "raw.asset1.filename=${newFileName}" \
        -u admin:123456
    """
}

// 通过nexus插件上传
def PushArtifactByNexusPlugin(artifactId, file, type, groupId, repoName, version){
    println(artifactId)
    println("${file}, ${type}, ${groupId}, ${repoName}, ${version}")
    nexusArtifactUploader artifacts: [[artifactId: artifactId, 
                                    classifier: '', 
                                    file: file, 
                                    type: type]], 
                        credentialsId: '6642b556-2779-43f8-9336-fbea2a0caaec', 
                        groupId: groupId, 
                        nexusUrl: '172.31.1.10:8081', 
                        nexusVersion: 'nexus3', 
                        protocol: 'http', 
                        repository: repoName, 
                        version: version    
}

// 通过maven命令上传
def PushArtifactByMavenCLI(artifactId, file, type, groupId, repoName, version){
    sh """
        mvn deploy:deploy-file \
        -DgroupId=${groupId} \
        -DartifactId=${artifactId} \
        -Dversion=${version} \
        -Dpackaging=${type}  \
        -Dfile=${file} \
        -Durl=http://172.31.1.10:8081/repository/${repoName}/  \
        -DrepositoryId=mymaven
    """
}

// 通过maven命令读取pom上传
def PushArtifactByMavenPom(repoName, file){
    sh """
        mvn deploy:deploy-file \
        -DgeneratePom=true \
        -DrepositoryId=mymaven \
        -Durl=http://172.31.1.10:8081/repository/${repoName}/ \
        -DpomFile=pom.xml \
        -Dfile=${file}
    """
}
