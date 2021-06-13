#!/bin/sh

echo '------------------------'
echo 'STARTING DEVICE SERVICE'
echo $MARIA_DB_USER

export HOME=/root
export MARIA_DB_USER=pi
export MARIA_DB_PWD=raspberry
# extract wlan0 mac address
export TRNKA_DEVICE_ID=$(cat /sys/class/net/wlan0/address)

logFile="/home/pi/trnka-device/logs/log-`date '+%Y-%m-%d-%H:%M:%S'`.log"
touch $logFile
nohup java -DMARIA_DB_USER=pi -DMARIA_DB_PWD=raspberry -DTRNKA_SOUNDS_LOCATION=/home/pi/trnka-device/sounds/ -jar /home/pi/trnka-device/trnka-device.jar >> $logFile 2>&1 &
