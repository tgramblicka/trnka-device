#!/bin/sh

echo '------------------------'
echo 'STARTING DEVICE SERVICE'

# extract wlan0 mac address
export TRNKA_DEVICE_ID=$(cat /sys/class/net/wlan0/address)

export HOME=/root
export MARIA_DB_USER=pi
export MARIA_DB_PWD=raspberry
export TRNKA_VST_REST_URL=http://192.168.0.101:8080
export TRNKA_SOUNDS_LOCATION=/home/pi/trnka-device/sounds/


logFile="/home/pi/trnka-device/logs/log-`date '+%Y-%m-%d-%H:%M:%S'`.log"
touch $logFile
#nohup java -DMARIA_DB_USER=pi -DMARIA_DB_PWD=raspberry -DTRNKA_SOUNDS_LOCATION=/home/pi/trnka-device/sounds/ -jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &
nohup java -jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &
