#!/bin/bash

#Script started sound
aplay /home/pi/Enamel/AudioFilesForScript/beep1.wav

#configurations (mode refers to our custom "config")

source config.txt

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
		
			## #3.1 while checking config. check if onefileplay = true, in which case just play. else, copy and then run.	
			if test $onefileplay = true
			then
			
				script="/mnt/usb/*.txt"
				
				su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_USB_WITH_FILE_1 $script'"
				
				#unmount AFTER java runs
				aplay /home/pi/Enamel/AudioFilesForScript/beep2.wav
				sudo umount /dev/sda1
			
			## #3.2 check config, check if onefileplay = false, then copy the files over to USBBuffer before playing. 
			elif test $onefileplay = false
			then	
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
				
		## #4, multiple files on USB
		elif test $numberoffiles -gt 1
		then
		
			#4.1 - check if multifilecopy is true. If so, copy files over
			if test $multifilecopy = true
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
			
			#4.2 - check if multifilecopy = false, if so, then just play the FIRST file.
			elif test $multifilecopy = false
			then
				#grab the first file
				script=`ls /mnt/usb/ | grep .txt | head -1`
				su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_USB_WITH_FILE_1 $script'"
				#unmount AFTER java runs
				aplay /home/pi/Enamel/AudioFilesForScript/beep2.wav
				sudo umount /dev/sda1
			fi
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
			su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_HIGH_LEVEL_SELECTOR $factdir $buffdir'"
		fi
	else
		echo "Script error (Just for inhouse for now, Sunny, David, Bil) - didn't go through if or elif, see happened" >> /home/pi/SCALPerrorlog.txt
	fi




















