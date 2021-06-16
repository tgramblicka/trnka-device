## Raspbian prerequisites:
Raspbian needs to be on version 10 buster. Won't work with version 9 stretch. Reason is that MariaDB10.1 (highest possible on 9 - stretch) does not support Sequences. Sequences are supported from MariaDB version 10.3+. MariaDB 10.3+ on raspbian requires a version 10 - buster.  

See tutorial how to upgrade stretch to buster: https://pimylifeup.com/upgrade-raspbian-stretch-to-raspbian-buster/



## Database update with liquibase on raspberry: 
1. connect to raspberry
2. add character sets of mysql to utf8 if needed in ```/etc/mysql/my.cnf``` to
   ```
   character-set-server=utf8 
   collation-server=utf8_bin
   ```
   and restart mysql DB      
2. connect to mariaDB
3. ```mysql -u root -p  ( pwd is raspberry)```
4. create DB with "utf8_slovak_ci" encoding
   ```
   SHOW DATABASES;
   drop database `trnka-device`;
   CREATE DATABASE `trnka-device` CHARACTER SET = 'utf8' COLLATE = 'utf8_bin';
   SET character_set_server = 'utf8';
   SET collation_connection = 'utf8_bin';
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
   grant access to laptop client (NOT NEEDED ANYMORE) 
      
```   
   mysql -u root -p (prihlas sa ako admin)
   GRANT ALL ON `trnka-device`.* TO 'root'@'192.168.0.101' IDENTIFIED BY 'raspberry';
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
1. make sure `production` profile is set in application.properties
2. make sure Audio renderer is setup in RendererConfig
3. build jar ```mvn clean package -Dmaven.test.skip=true```
4. run the copy.sh script from project root directory. Uncomment only needed parts
```
./scripts/copy.sh
```
5. copy also the necessary run.sh which is in copy.sh
6. copy also the audio files if they changed


### How to create SD card image ###
1. remove the SD card from raspberry
2. use the Samsung SD card reader (white) and put SD inside
3. use the "USB Image Tool" app ( installed on work laptop)

   
###   How to DUMP the DB ###
   ```
   mysqldump --databases trnka-device > /home/pi/trnka-device/trnka-device.sql
   ```
   
   
   
### LOCAL Windows / Mac setup for DB ###
Set character_set_server in your C:\Program Files\MariaDB 10.4\data to
   ```
   character-set-server=utf8 
   collation-server=utf8_bin
   ```

### Raspberry ENV variables and startup commands ###
All the env variables and startup scripts are located in run.sh script. This script is executed from /etc/rc.local as well as startup audio:

python3 /home/pi/trnka-device/wait_until_program_starts.py
```
cat /etc/rc.local
```


### Raspberry MYSQL commands examples ###
Connect and select:
```
   mysql -u root -p  
   SHOW DATABASES;
   use trnka-device;
   select * from user;
```


### Compiling Kernel in Virtual Box Ubuntu ###
- install VirtualBox
- Download Ubuntu 20+ ISO
- Create new Virtual Machine in VirtualBox ( 1GB RAM & at least 15GB disk space)
- Install Ubuntu
- git clone raspberry 5.10 git repo into Ubuntu https://www.raspberrypi.org/documentation/linux/kernel/building.md  
- Use menuconfig tool https://www.raspberrypi.org/documentation/linux/kernel/configuring.md 
```
make ARCH=arm CROSS_COMPILE=arm-linux-gnueabihf- menuconfig
```
- enable TCA8418 with Y ( * )
- copy the tublet-overlay.dts into linux's arch/arm/boot/dts/overlays/  
- Compile kernel 
- After compiled insert SD Card & mount it to virtual box http://rizwanansari.net/access-sd-card-on-linux-from-windows-using-virtualbox/
- check with lsblk -o NAME,FSTYPE,SIZE,MOUNTPOINT,LABEL
- trouble shooting of SDCARD mounting - make sure IO Cache is enabled: https://scribles.net/accessing-sd-card-from-linux-virtualbox-guest-on-windows-host/


### Setting up raspberry ###
- Make faster boot: raspi-config > system-options > network & boot (disable wait for network until booot)






