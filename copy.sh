#!/bin/bash

#mvn clean compile assembly:single
#mvn clean install


#scp ./target/trnka-device.jar pi@192.168.1.15:trnka-device/
#scp pi@192.168.1.15:/home/pi/audio/* ./
rsync --progress -avz target/trnka-device.jar pi@192.168.1.15:/home/pi/trnka-device




