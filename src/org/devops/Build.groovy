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
    sh '/usr/locla/go/bin/go build demo.go'
}

def YarnBuild() {
    sh ‘’
}