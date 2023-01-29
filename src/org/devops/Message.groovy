package org.devops


// 企业微信
def WeiXin(){
    withCredentials([string(credentialsId: 'b8168f6e-3bb0-4dc1-bd01-2b2348cd089a', variable: 'ACCESS_TOKEN')]) {
        sh """
            curl --location --request POST 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=3979ef4b-c9c1-4192-9fbb-e5d16c1ec522' \
                --header 'Content-Type: application/json' \
                --data '{
                    "msgtype": "markdown",
                    "markdown": {
                        "content": "## ${JOB_NAME}作业构建信息: \n  ### 构建人：${env.BUILD_USER} \n   ### 作业状态： ${currentBuild.currentResult} \n  ### 运行时长： ${currentBuild.durationString} \n  ###### 更多详细信息点击 [构建日志](${BUILD_URL}/console) \n"
                    }
                }'
        """
    }
}
// 钉钉通知
def DingDing(){
    withCredentials([string(credentialsId: '1fbae655-b543-4667-aa63-f48451e384b8', variable: 'ACCESS_TOKEN')]) {
        // some block
        sh """
            curl --location --request POST "https://oapi.dingtalk.com/robot/send?access_token=${ACCESS_TOKEN}" \
                --header 'Content-Type: application/json' \
                --data '{
                    "msgtype": "markdown",
                    "markdown": {
                        "title": "DEVOPS通知",
                        "text": "## ${JOB_NAME}作业构建信息: \n  ### 构建人：${env.BUILD_USER} \n   ### 作业状态： ${currentBuild.currentResult} \n  ### 运行时长： ${currentBuild.durationString} \n  ###### 更多详细信息点击 [构建日志](${BUILD_URL}/console) \n"
                    },
                    "at": {
                        "atMobiles": [
                            "158115965723"
                        ],
                        "atUserIds": [
                            "user123"
                        ],
                        "isAtAll": true
                    }
                }'
        """
   }
}
