#!/bin/bash


mvn clean compile assembly:single
java -jar target/trnka-device-jar-with-dependencies.jar


