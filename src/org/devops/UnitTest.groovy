package org.devops

//Maven
def MavenTest() {
    sh "/usr/local/maven/bin/mvn test"
    junit 'target/surefire-reports/*.xml'
}

//Gradle
def GradleTest() {
    sh '/usr/local/gradle/bin/gradle test'
    junit 'build/test-results/test/*.xml'
}

//Golang
def GoTest() {
    sh '/usr/local/go/bin/go test'
}

//Npm
def NpmTest() {
    sh '/usr/local/node/bin/npm test'
}

//Yarn
def YarnTest() {
    sh '/usr/local/node/bin/yarn test'
}

//Main
def CodeTest(type) {
    switch(type) {
        case "maven":
            MavenTest()
            break;
        case "gradle":
            GradleTest()
            break;
        case "npm":
            NpmTest()
            break;
        case "yarn":
            YarnTest()
            break;
        default:
            println("No such tools ... [maven/gradle/npm/yarn/go]")
            break
    }
}
