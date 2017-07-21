#!/bin/bash

#Script started sound
aplay /home/pi/Enamel/AudioFilesForScript/beep1.wav

#configurations (mode refers to our custom "config")
mode=DEFAULT	
shopt -s globstar

factdir="/home/pi/Enamel/FactoryScenarios/"
buffdir="/home/pi/Enamel/USBBuffer/"
enameljarpath="/home/pi/Enamel/Enamel.jar"
javapath="/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/jre/bin/java"

#Check if Java process is currently running. If so, terminate them.
if test `ps -ef | grep java | wc -l` -ne 1
then
	sudo pkill -f 'java -jar'
fi	

#mount the drive
sudo mount /dev/sda1 /mnt/usb

mountsuccess=$?

##Test if drive mounting successful (i.e. drive is plugged in) $? should equal 0
##REFERS TO #3 and #4   
if test $mountsuccess -eq 0
then
	srcdir="/mnt/usb/"
	destdir=$buffdir

	numberoffiles=`ls -l $srcdir*.txt | wc -l`

	## #3, single file on USB
	if test $numberoffiles -eq 1
	then 
		script="/mnt/usb/*.txt"
		
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_USB_WITH_FILE_1 $script'"
		
		#unmount AFTER java runs
		aplay /home/pi/Enamel/AudioFilesForScript/beep2.wav
		sudo umount /dev/sda1
		
		##OR copy them over depending on the current $mode
	
	## #4, multiple files on USB
	elif test $numberoffiles -gt 1
	then
		##NEED TO COPY OVER SOUND FILES AND FOLDER STRUCTURE			

		for file in /mnt/usb/*.txt
		do
        		if test -f "$file"
        		then
                		cp "$file" "$destdir"
        		fi
		done
		
		#unmount before starting Java
		sudo umount /dev/sda1
		aplay /home/pi/Enamel/AudioFilesForScript/beep2.wav
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_HIGH_LEVEL_SELECTOR $factdir $buffdir'"
	fi


#drive mounting is unsuccessful (no mountable drive available)
### REFERS TO #1 and #2

elif test $mountsuccess -ne 0
then
	#Check if usb buffer is empty
	USBBuffer="/home/pi/Enamel/USBBuffer/"
	numberoffilesbuffer=`ls -l $USBBuffer*.txt | wc -l`
	if test $numberoffilesbuffer -eq 0
	then
		## #1
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_FACTORY $factdir'"		
	else
		## #2
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_HIGH_LEVEL_SELECTOR $factdir $buffdir'"
	fi
else
	echo "Script error (Just for inhouse for now, Sunny, David, Bil) - didn't go through if or elif, see happened" >> /home/pi/SCALPerrorlog.txt
fi




















