#!/bin/sh

echo '------------------------'
echo 'STARTING DEVICE SERVICE'

# extract hostname, IPaddress , macAddress
export TRNKA_DEVICE_ID="$(echo `hostname`)___$(echo `hostname -I`)___$(cat /sys/class/net/wlan0/address)"

export HOME=/root
export MARIA_DB_USER=pi
export MARIA_DB_PWD=raspberry
export TRNKA_VST_REST_URL=http://redacted:8080
export TRNKA_SOUNDS_LOCATION=/home/pi/trnka-device/sounds/
export TRNKA_CLIENT_USERNAME=redacted
export TRNKA_CLIENT_PASSWORD=redacted


logFile="/home/pi/trnka-device/logs/log-`date '+%Y-%m-%d-%H:%M:%S'`.log"
touch $logFile
#nohup java -DMARIA_DB_USER=pi -DMARIA_DB_PWD=raspberry -DTRNKA_SOUNDS_LOCATION=/home/pi/trnka-device/sounds/ -jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &
nohup java -jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &

# uncomment below if need to run with jmx enabled
#nohup java -Dcom.sun.management.jmxremote \
#-Dcom.sun.management.jmxremote.port=9010 \
#-Dcom.sun.management.jmxremote.rmi.port=9010 \
#-Dcom.sun.management.jmxremote.local.only=false \
#-Dcom.sun.management.jmxremote.authenticate=false \
#-Dcom.sun.management.jmxremote.ssl=false \
#-Djava.rmi.server.hostname=192.168.0.107 \
#-jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &
