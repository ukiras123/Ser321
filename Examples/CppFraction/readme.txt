Purpose: demonstrate simple g++ command line compilation and debugging.

Ser321 Principles of Distributed Software Systems

see http://pooh.poly.asu.edu/Ser321

Author: Tim Lindquist (Tim.Lindquist@asu.edu), ASU Polytechnic, Engineering
Version: July 2016

Compilation is not the same on all systems for which gcc is hosted. This example
has been developed to work on both Mac OS X and Linux.

1. A script to compile and link provided with this example, called buildscript.
To get buildscript to work, you may need to change the permissions on the buildscript
allowing the user to execute the script. Do this with:
chown u+x buildscript
This is necessary since Jar strips away execute permissions on these files. You can
give them execute permission with the following command:

2. The script is two-step. First it compiles to create .o file for each compilation
module. Next, it links these to create the executable (fraction).

in one line it can be compiled using:
g++ -g -ggdb -std=c++11 src/Fraction.cpp src/main.cpp -lstdc++ -o fraction

To comile for debugging, use the debug switch -g -ggdb and generate
object files. This requires two steps since gcc does the following when
invoked to compile and link:
 1. Compiles to create the assembly language files(s) randomName.s
 2. Assembles the .s files create object file(s): randomName.o
 3. Link the .o files with appropriate libraries to create an executable
 4. Delete the .o and .s files
To debug, we need the .o files augmented with symbol information for
debugging. To retain the .o files we use separate compile commands,
first with the -c switch (compile only) and second to link the .o files.
We also use the -g switch to generate debugger information.

To compile on Debian Linux from a terminal whose current directory is the project
directory (CppFraction) enter:
./buildscript

To run the program using the degugger gdb do:
gdb fraction
break main    // or: break main.cpp:46
run
step
list
step
print aFract->numerator
step
print numerator
set numerator = 3
step
cont
...

To see the Berkeley GDB reference card go to:
http://pooh.poly.asu.edu/Ser321/Resources/doc/gdb-refcard.pdf

To compile and link the program using make use the following:
make
./fraction
make clean

The output of this program should be:
The fraction is: 0/1
The fraction is: 1/3

To compile and link the program using Ant use the following:

First make sure the cpptasks.jar task definitions are found by
your installation of Ant. If you haven't already done this, to the
following:
Included in the project directory is an Ant build file: build.xml
Also, in the lib directory is the jar file: anttasks.jar. Copy that
file to your home directory and extract it. That is from the JavaFraction
folder:
cp lib/anttasks.jar ~
pushd ~
jar xf anttasks.jar
pushd -0

To execute using Ant do the following:
ant execute
