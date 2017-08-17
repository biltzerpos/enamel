Where to put the files:
	enamel.desktop (Enamel Player Autostart) - /home/pi/.config/autostart/
	SCALP.sh, startSCALP.sh, unmountDrive.sh, changeconfig.sh, config.txt - /home/pi/
	enamel_usb_autostart.rules - /etc/udev/rules.d/

Need to install 'at' command:
	(in terminal) sudo apt-get install at

Install Java 1.8
	(in terminal) sudo apt-get install oracle-java8-jdk
	
Set Java path to Java 1.8
	(in terminal) sudo update-alternatives --config java

The scripts assume that you've compiled and exported the Enamel Java project in .jar, and placed it in /home/pi/Enamel/.
	/home/pi/Enamel/ contains:
		AudioFilesForScript/
		FactoryScenarios/
		mbrola/
		USBBuffer/
		Enamel.jar
		all the relevant .jar libraries that Enamel.jar uses
