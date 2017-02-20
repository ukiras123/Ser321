Purpose: demonstrate simple Java command line compilation and
debugging, and using Ant to build java.

Ser321 Principles of Distributed Software Systems

see http://pooh.poly.asu.edu/Ser321

Author: Tim Lindquist (Tim.Lindquist@asu.edu), ASU Polytechnic, Engineering
Version: August 2015

Independent of the system you are on, the following command should work.

1. Compiling and running the debugger from the command line.

cd src
javac -d . -g Fraction.java
jdb -classpath . Fraction
stop in Fraction.main
stop in Fraction.Fraction
stop in Fraction.setNumerator
run                    // the program will break at the first line
                       // use the commands below to control execution:
                       // cont (continue),
                       // print expression
                       // set lvalue = expression
                       // step

2. Running with Ant
Included in the project directory is an Ant build file: build.xml
Also, in the lib directory is the jar file: anttasks.jar. Copy that
file to your home directory and extract it. That is from the JavaFraction
folder:
cp lib/anttasks.jar ~
pushd ~
jar xf anttasks.jar
pushd -0

At the command line with the current directory JavaFraction, type ant.
The default target provides a message indicating available targets:

To prepare, compile and execute the Fraction program, run the execute target.
ant execute


The output of this program should be:
The fraction is: 1/3
