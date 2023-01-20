package org.devops

def MavenBuild() {
    sh '/usr/local/apache-maven-3.8.7/bin/mvn clean package'
}

def GradelBuild() {
    sh '/usr/local/gradle/bin/gradle build'
}

def NpmBuild() {
    sh '/usr/local/node/bin/npm install && /usr/local/node/bin/npm run build'
}

def Gobuild() {
    sh '/usr/local/go/bin/go build demo.go'
}

def YarnBuild() {
    sh '/usr/local/node/bin/yarn && /usr/local/node/bin/yarn build'
}

def CodeBuild(type) {
    switch(type) {
        case "maven":
            MavenBuild()
            break;
        case "gradle":
            GradelBuild()
            break;
        case "npm":
            NpmBuild()
            break;
        case "yarn":
            YarnBuild()
            break;
        default:
            error "No such tools ... [maven/gradle/npm/yarn/go]"
            break
    }
}



