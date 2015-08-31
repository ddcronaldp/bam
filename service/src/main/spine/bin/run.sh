#! /bin/sh

thisUser=`echo $USER`;
if [ "${thisUser}" != "app" ]; then
   echo "Please run as user: app"
   exit
fi

# This is because the 10.39 address is the 10Gb nic on the grid edition servers.
export PRIVATE_IP=`/sbin/ifconfig -a | awk '/(cast)/ { print $2 }' | cut -d':' -f2 | grep -F "10." | sort -n -t. -r | head -n1`

export APP_HOME=/opt/apps/admiral-services
export JAVA_HOME=/usr/java/jdk1.8.0_40/
export APP_NAME=admiral-services

export START_MEM=4g
export MAX_MEM=4g
export MAX_GC_PAUSE_MILLIS=100

# use g1
export USE_JAVA7_SETTINGS=true

chmod 755 $APP_HOME/bin/app-wrapper
chmod 755 $APP_HOME/bin/wrapper

$APP_HOME/bin/app-wrapper $@
