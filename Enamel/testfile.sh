#!/bin/bash

#Find and change the "mode=" text.
sleep 1
changemode=$1
sed -i 's/^mode=.*$/mode=`changemode`/' /home/pi/SCALP.sh