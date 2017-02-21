Author: Tim Lindquist (Tim.Lindquist@asu.edu)
        Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
Version: August 2015

See http://pooh.poly.asu.edu/Ser321

Purpose: Sample project showing Java and C++ media playback and Assign5, Assign6
gui classes. The Java program includes the src/java/controller/SampleMediaClient.java
source code, together with its base class, which is provided as compiled class
files. Beware that the class files were compiled with Oracle JavaSE version 1.8.0.
If you are using an earlier version of Java, then compilation and execution will
most likely fail.

The Java program uses javax.swing.* components to present a view that includes
a JTree in the left panel and a panel for composing and displaying details of a
media description in the right pane.

You are encouraged to use this view in its pre-compiled form to create your solution
to Assignment 6. Create your client using the SampleMediaClient.java controller
found in src/java/controller

The javadocs for the view class ser321.media.MediaClientGui can be found by opening
doc/java/index.html

To build the Java program:
ant build.java

You should execute the Java program first with:
java -cp classes:lib/mediaGui.jar ser321.media.SampleMediaClient

The C++ program uses the library package FLTK (full light toolkit). Setting up
this library on Debian Wheezy Linux can be done with:
sudo apt-get install libfltk1.3-dev

The source code for both the view (src/cpp/MediaClientGui.cpp) and for the
controller (src/cpp/MediaClient.cpp) are included in this project.

The javadocs for the view class MediaClientGui.cpp can be found by opening
doc/cpp/html/index.html

Build the C++ version with:
ant build.cpp

and execute the C++ version with:
./bin/sampleMediaClient

Read the source code, both C++ and Java. The only menu functions implemented
in the source code are File-->Exit and Media-->Play
Both clients are written to play the included music file (mp3) and video file (mp4).
Neither of the media files are protected, and their distribution does not violate any
copyright laws. In my defense, that was the primary criteria in their selection.

The clients are written to provide Ser321 students with the basis for constructing their
solutions for the C++ media player and browser and for the Java media player and browser.
