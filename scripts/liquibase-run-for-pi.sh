#!/bin/sh


mariaDbClientJar="/c/Users/200000591/apps/mariadb-java-client-2.4.3.jar"
raspi_host="jdbc:mariadb://192.168.0.110:3306/trnka-device"
pwd="root"
raspi_pwd="raspberry"


# LIQUIBASE FOR PC
liquibase \
--changeLogFile="./src/main/resources/db/changelog/changelog-master.xml" \
--url=$raspi_host \
--driver="org.mariadb.jdbc.Driver" \
--classpath="$mariaDbClientJar" \
--username="root" \
--password=$raspi_pwd \
--logLevel="INFO" \
update






