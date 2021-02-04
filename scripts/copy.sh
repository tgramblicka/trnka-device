#!/bin/bash

#mvn clean compile assembly:single
#mvn clean install


#scp ./target/trnka-device.jar pi@192.168.0.104:trnka-device/
#scp pi@192.168.0.104:/home/pi/audio/* ./
#scp ./* pi@192.168.0.104:/home/pi/audio/


# copy jar
#rsync --progress -avz target/trnka-device.jar pi@192.168.0.110:/home/pi/trnka-device

#copy sounds
#rsync --progress -avz ./audio/commands/* pi@192.168.0.110:/home/pi/trnka-device/sounds/commands
#rsync --progress -avz ./audio/letters/* pi@192.168.0.110:/home/pi/trnka-device/sounds/letters


#copy wait script
#rsync --progress -avz ./wait_until_program_starts.py pi@192.168.0.104:/home/pi/trnka-device






