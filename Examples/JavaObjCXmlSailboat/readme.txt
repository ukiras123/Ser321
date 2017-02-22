Purpose: demonstrate externalizing an object to/from xml in
a format thats compatible with java beans xml decoder/encoder.
This example consists of both Java and Objcective-C programs.
Both programs are command line programs that interact to create
new sailboat objects, print them xml serialize/deserialize to/from
a file. The xml format is shared (readable and writeable) by programs
from either language.

Cst502 Emerging Languages and Programming Technologies

see http://pooh.poly.asu.edu/Cst502

Author: Tim Lindquist (Tim.Lindquist@asu.edu), ASU Polytechnic, Engineering
Version: December 2011

To run the Objective-C Sailboat on GNUstep: From a command line in the
project directory (MinGW Shell):
make
./obj/SailboatGNUstep.exe
make clean       //to remove .o and executable files

To run the Objective-C Sailboat on Mac OS X: From a bash shell in the
project directory
(Utilities --> Terminal window)
chmod u+x xcruncmd   //execute one time only after jar xvf boat.jar

./xcruncmd
./obj/SailboatMac
rm -rf ./obj    //to clean

There is also an Ant build file included. Ant targets are:
ant buildjava
ant buildobjc
ant clean
To execute the Java Sailboat. From the command line in the project directory
java -cp classes cst502.objcXml.Sailboat

Here are the commands that are recognized by both versions of the program:
>new Contessa32 Ceres 9.5 9500 24      //define a new boat named Ceres
>write Ceres                           //serializes Ceres to Ceres.xml
>read Ceres                            //deserialize Ceres from Ceres.xml
>list                                  //list the names of all defined boats
>print Ceres                           //print information about Ceres
>end                                   //exit the program
