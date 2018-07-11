#!/bin/bash

ENV=dev
RUNNING_USER=dongann
ADATE=`date +%Y%m%d%H%M%S`
APP_NAME=dongann-app-impl-0.0.1-SNAPSHOT

cd ..
APP_HOME=`pwd`
dirname $0|grep "^/" >/dev/null
if [ $? -eq 0 ];then
   APP_HOME=`dirname $0`
else
    dirname $0|grep "^\." >/dev/null
    retval=$?
    if [ $retval -eq 0 ];then
        APP_HOME=`dirname $0|sed "s#^.#$APP_HOME#"`
    else
        APP_HOME=`dirname $0|sed "s#^#$APP_HOME/#"`
    fi
fi

if [ ! -d "$APP_HOME/log" ];then
  mkdir $APP_HOME/log
fi

# classpath设置
LIB_DIR=$APP_HOME/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
CLASS_PATH=$CONF_DIR:$LIB_JARS

LOG_PATH=$APP_HOME/log/$APP_NAME.out
#GC_LOG_PATH=$APP_HOME/log/gc-$APP_NAME-$ADATE.log
#JMX监控需用到
JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1091 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
#JVM参数
#JVM_OPTS="-Dname=$APP_NAME -Djeesuite.configcenter.profile=$ENV -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
JVM_OPTS="-Dname=$APP_NAME -Djeesuite.configcenter.profile=$ENV -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"

JAR_FILE=$APP_NAME.jar
pid=0
start(){
  checkpid
  if [ ! -n "$pid" ]; then
    nohup java -classpath $CLASS_PATH -jar $JVM_OPTS lib/$JAR_FILE > $LOG_PATH 2>&1 &
#    nohup java -classpath $CLASS_PATH -jar $JVM_OPTS lib/$JAR_FILE > /dev/null 2>&1 &
    echo "---------------------------------"
    echo "启动完成，按CTRL+C退出日志界面即可>>>>>"
    echo "---------------------------------"
    sleep 2s
#    tail -f $LOG_PATH
  else
      echo "$APP_NAME is runing PID: $pid"
  fi

}


status(){
   checkpid
   if [ ! -n "$pid" ]; then
     echo "$APP_NAME not runing"
   else
     echo "$APP_NAME runing PID: $pid"
   fi
}

checkpid(){
    pid=`ps -ef |grep $JAR_FILE |grep -v grep |awk '{print $2}'`
}

stop(){
    checkpid
    if [ ! -n "$pid" ]; then
     echo "$APP_NAME not runing"
    else
      echo "$APP_NAME stop..."
      kill $pid
    fi
}

restart(){
    stop
    sleep 1s
    start
}

case $1 in
          start) start;;
          stop)  stop;;
          restart)  restart;;
          status)  status;;
              *)  echo "require start|stop|restart|status"  ;;
esac