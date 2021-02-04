## Database update with liquibase on raspberry: 
1. connect to raspberry
2. connect to mariaDB
3. ```mysql -u root -p  ( pwd is raspberry)```
4. 
   ```
   SHOW DATABASES;
   drop database `trnka-device`;
   create database `trnka-device`;
   ```
## Executing liquibase from local PC on raspberry
1. Add access to client ( not necessarily needed ) : https://websiteforstudents.com/configure-remote-access-mysql-mariadb-databases/
 ```
   sudo nano /etc/mysql/my.cnf
   add to the end of the file:
   bind-address=0.0.0.0
```    
2. restart mysql service
   ```sudo service mysql restart``` 
3.
   grant access to laptop client 
      
```   
   mysql -u root -p (prihlas sa ako admin)
   GRANT ALL ON `trnka-device`.* TO 'root'@'192.168.1.24' IDENTIFIED BY 'raspberry';
   GRANT ALL ON `trnka-device`.* TO 'pi'@'localhost' IDENTIFIED BY 'raspberry';
```   
      
4. run liquibase script from local PC from project root
```
./scripts/liquibase-run-for-pi.sh
```
5. uncomment the bind address cause ```mysql -u root -p``` won't be able to login
6. connect to DB from raspberry and check whether all was updated:
```
 mysql -u root -p (log as admin)
 select * from `trnka-device`.sequence;
```   

## build & copy new jar to raspberry
1. make sure prod profile is set in application.properties
2. make sure Audio renderer is setup in RendererConfig
3. build jar ( mvn clean package)
4. use the copy.sh script containing followingly from project root directory. Uncomment only needed parts
```
./scripts/copy.sh
```


## How to create SD card image
1. remove the SD card from raspberry
2. use the "USB Image Tool" app

   
###
   How to DUMP the DB
   ```
   mysqldump --databases trnka-device > /home/pi/trnka-device/trnka-device.sql
   ```

