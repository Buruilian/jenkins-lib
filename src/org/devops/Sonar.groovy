package org.devops

def CodeScan(branchName) {
    println("Code Scan")
    withCredentials([usernamePassword(credentialsId: 'f7d0c5f7-2803-4d86-9e5e-d0160f474bf0', 
                    passwordVariable: 'SONAR_PASSWORD', 
                    usernameVariable: 'SONAR_USER')]) {
        sh """/usr/local/sonar-scanner/bin/sonar-scanner \
            -Dsonar.login=${SONAR_USER} \
            -Dsonar.password=${SONAR_PASSWORD} \
            -Dsonar.projectVersion=${branchName}
           """
       }
}
