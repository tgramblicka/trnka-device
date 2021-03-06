#!/bin/sh

mariaDbClientJar="/c/Users/200000591/apps/mariadb-java-client-2.4.3.jar"

liquibase \
--changeLogFile="./src/main/resources/db/changelog/changelog-master.xml" \
--url="jdbc:mariadb://localhost:3306/trnka-device" \
--driver="org.mariadb.jdbc.Driver" \
--classpath="$mariaDbClientJar" \
--username="root" \
--password="root" \
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

