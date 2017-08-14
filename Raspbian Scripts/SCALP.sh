#!/bin/bash

##########TODO##########
#Need to do something about usbbuffersize - audiofiles are a big concern. How do we remove audiofiles that are specific to that particular script?

##Variables needed for all modes are here as well as some settings for the script to function.
##Absolute paths are needed because this script is called from udev, which executes this script as the root user, not the current user.

playsound="aplay /home/pi/Enamel/AudioFilesForScript/beep1.wav"
playunmountsound="aplay /home/pi/Enamel/AudioFilesForScript/beep2.wav"

source config.txt					#configuration variables from "config.txt"
#shopt -s globstar					#Probably don't need this.
kernel=$1						#get the kernel number of USB, passed from udev to startSCALP.sh to this script (SCALP.sh).

factdir="/home/pi/Enamel/FactoryScenarios/"		#The absolute path for the factory directory on the TBB.
buffdir="/home/pi/Enamel/USBBuffer/"			#The absolute path for the USB Buffer directory on the TBB.
enameljarpath="/home/pi/Enamel/Enamel.jar"		#The absolute path for the java program.
javapath="/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/jre/bin/java"	#The absolute path for Java JDK.

##Copy the .txt scenario files and .wav audio files from the USB device to the USB Buffer.
copy_from_usb_and_play() {
	for file in $script
	do
		if test -f "$file"
		then
			cp "$file" "$destdir"
		fi
	done
		
	##if AudioFiles exist on the USB, copy
	if test -d "$srcaudiodir"	
	then
		for file in "$audiofile" #"$srcaudiodir""*.wav"
		do
			cp "$file" "$destaudiodir"
		done
	fi

	##unmount before starting Java
	sudo umount "/dev/$kernel"	
	$playunmountsound
	su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_HIGH_LEVEL_SELECTOR $factdir $buffdir'"
}

##Play the .txt scenario file directly from the USB device.
play_from_usb() {
	##unmount AFTER java runs
	su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_USB_WITH_FILE_1 $script'"	
	$playunmountsound
	sudo umount "/dev/$kernel"
}

##Play the .txt scenario file from the factory directory or start the high level selector in Java, depending on whether USB Buffer has any files.
play_from_pi() {
	##if usbbuffer empty, then play from factory
	numberoffilesbuffer=`ls -l $buffdir*.txt | wc -l`
	if test $numberoffilesbuffer -eq 0
	then
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_FACTORY $factdir'"		
	else
		su pi -c "DISPLAY=:0 bash -c '$javapath -jar $enameljarpath START_HIGH_LEVEL_SELECTOR $factdir $buffdir'"
	fi
}

$playsound	#script successfully started!

#Check if Java process is currently running. If so, terminate them.
if test `ps -ef | grep java | wc -l` -ne 1		
then
	sudo pkill -f 'java -jar'
fi	

sudo mount "/dev/$kernel" "/mnt/usb"	#mount the drive
mountsuccess=$?				#mounting drive exit code.

##Test if drive mounting successful (i.e. drive is plugged in) $? should equal 0. If so, start operating on the files on the USB device,
##whether it's copy files over to the TBB or play directly from the USB device.
if test $mountsuccess -eq 0
then
	srcdir="/mnt/usb/"
	srcaudiodir="$srcdir""AudioFiles/"
	
	script="$srcdir""*.txt"
	audiofile="$srcaudiodir""*.wav"

	destdir="$buffdir"
	destaudiodir="$destdir""AudioFiles/"
	
	numberoffiles=`ls -l $srcdir*.txt | wc -l`

	# #3, single file on USB.
	if test $numberoffiles -eq 1
	then 
		# #3.1 check config. check if onefileplay = true, in which case just play. else, copy and then run.
		if test $onefileplay = true	
		then
			play_from_usb
	
		# #3.2 check config, check if onefileplay = false, then copy the files over to USBBuffer before playing. 				
		elif test $onefileplay = false	
		then	
			copy_from_usb_and_play
		fi
			
	# #4, multiple files on USB
	elif test $numberoffiles -gt 1
	then
		# #4.1 - check if multifilecopy is true. If so, copy files over.
		if test $multifilecopy = true	
		then
			copy_from_usb_and_play
		
		# #4.2 - check if multifilecopy = false, if so, then just play the FIRST file.
		elif test $multifilecopy = false	
		then
			script="$srcdir""`ls "$srcdir" | grep .txt | head -1`"	#grab the first file
			play_from_usb
		fi

	#No suitable file found on the USB device, so just unmount the device and start play_from_pi.
	else		
		sudo umount "/dev/$kernel"
		$playunmountsound
		play_from_pi
	fi

#drive mounting is unsuccessful (no mountable drive available)
elif test $mountsuccess -ne 0
then
	play_from_pi
fi




















