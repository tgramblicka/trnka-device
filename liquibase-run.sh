#!/bin/sh

mariaDbClientJar="/c/Users/200000591/apps/mariadb-java-client-2.4.3.jar"
host="jdbc:mariadb://localhost:3306/trnka-device"
raspi_host="jdbc:mariadb://192.168.1.15:3306/trnka-device"
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






## for generating changelog from existing DB
#liquibase \
#--changeLogFile="./src/main/resources/db/changelog/changelog-masterrr.xml" \
#--url="jdbc:mariadb://localhost:3306/trnka-device" \
#--diffTypes="tables,columns,indexes,foreignkeys,primarykeys,uniqueconstraints" \
#--driver="org.mariadb.jdbc.Driver" \
#--classpath="$mariaDbClientJar" \
#--username="root" \
#--password="root" \
#--logLevel="INFO" \
#generateChangeLog

