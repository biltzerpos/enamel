#!/bin/bash

#Change a single config variable once per script run. 
#Some require "true" or "false" values, while one requires a valid integer. Checks and echos error messages if invalid option is given.
#If everything is valid, changes changeconfig.sh variable value with sed.
mode=$1
changed=$2
isnumber='^[0-9]+$'


if test $mode != "onefileplay" -a $mode != "multifilecopy" -a $mode != "usbbuffersize" -a $mode != "smartclobber"
then
	echo "invalid config variable you are trying to change. Must be one of \"onefileplay\", \"multifilecopy\", \"usbbuffersize\", or \"smartclobber\"."
	exit 1
fi

if test $mode = "onefileplay" -o $mode = "multifilecopy" -o $mode = "smartclobber"
then
	if test $changed != "true" -a $changed != "false"
	then
		echo "Changing onefileplay, multifileplay, or smartclobber needs have the value \"true\" or \"false\"."
		exit 1
	fi
fi

if test $mode = "usbbuffersize"
then
	if ! [[ $changed =~ $isnumber ]]
	then
		echo "for usbbuffersize, the value must be a valid integer."
		exit 1
	fi
fi	

echo did it get here >> /home/pi/temptemptemp.txt
sed -i "s/^$mode=.*$/$mode=$changed/" /home/pi/config.txt
echo $? >> /home/pi/temptemptemp.txt