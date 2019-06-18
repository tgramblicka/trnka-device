#!/bin/bash

mvn clean compile assembly:single
scp ./target/trnka-device-jar-with-dependencies.jar pi@192.168.1.15:trnka-device/

