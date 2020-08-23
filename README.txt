This is a command line driven mp3 player prototype built in Java. The program as out of the box
also will work though the NetBeens IDE.

The program makes use of the opensource Javazoom 1.0.1 jar library:
http://www.javazoom.net/index.shtml
This Javazoom player class helps with the decoding of MP3 files by creating
threads that read bytestreams from the files allowing the data to be fed to the users
audio chipset. Theads must be used and destoryed so as the user can continue to use
the program and system resources are not held up by the player class instance.

The build makes use also of doubly linked list for playlist so that the user my skip or go
back on songs. 

To create a playlist the user must add the Mp3 files into the 'user playlist' directory.

