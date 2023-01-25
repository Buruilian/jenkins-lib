#!/bin/bash
 
APP_NAME=$1
VERSION=$2
PORT=$3
 
start(){
    proc_result=$(ps -ef | grep java | grep "${APP_NAME}" | grep -v grep || echo "false")
    if [[ $proc_result == "false" ]];then
      nohup java -jar -Dserver.port=${PORT} ${APP_NAME}-${VERSION}.jar >${APP_NAME}.log  2>&1 &
    else
      echo "skip start"
    fi
}
 
stop(){
    pid=$(ps -ef | grep java | grep "${APP_NAME}" | awk '{print $2}')
    kill -15 $pid
}
 
check(){
    proc_result=$(ps -ef | grep java | grep "${APP_NAME}" | grep -v grep || echo "false")
    port_result=$(netstat -tpln | grep ${PORT} || echo "false")
    url_result=$(curl -s http://localhost:${PORT} || echo "false")
 
    if [[ $proc_result == "false" || $port_result == "false" || $url_result == "false" ]];then
      echo "service is not running..."
    else
      echo "service is  running..."
    fi
}
 
case $4 in
  start)
    start
    sleep 5s
    check
    ;;
  stop)
    stop
    sleep 5s
    check
    ;;
  restart)
    stop
    sleep 5s
    start
    sleep 5s
    check
    ;;
  check)
  check
    ;;
  *)
  echo "error input,please sh $0 start|stop|check"
esac
