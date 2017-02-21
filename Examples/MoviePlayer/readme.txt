Author: Tim Lindquist (Tim.Lindquist@asu.edu)
        Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
Version: January 2017

See http://pooh.poly.asu.edu/Ser321

Purpose: Sample project showing Java and C++ movie library browser and playback.
The GUI classes in this project are used to complete course MovieLibrary assignments.
The Java program includes the src/java/controller/SampleMovieClient.java
source code, together with its base class, which is provided as compiled class
files. Beware that the class files were compiled with Oracle JavaSE version 1.8.0
If you are using an earlier version of Java, then compilation and execution will
most likely fail. The course includes instructions for loading version 1.9 on both
the Raspberry Pi and on the Virtual Box Debian Linux.

The Java program uses javax.swing.* components to present a view that includes
a JTree in the left panel and a panel for composing and displaying details of a
movie description in the right pane.

You are encouraged to use this view in its pre-compiled form to create your solution
to course Movie Browser/Library assignments.
Create your client using the SampleMovieClient.java controller found in src/java/controller
folder as the basis for constructing your solution.

The javadocs for the view class ser321.movie.MovieLibraryGui can be found by opening
doc/java/index.html

To build the Java program:
ant build.java

You should execute the Java program first with:
java -cp classes:lib/movieGui.jar ser321.movie.SampleMovieClient TimLindquist 192.168.2.2 8888
or simply
ant execute.java

You should read the code so you understand the idioms necessary to accomplish the following:
- getting and setting the contents of text fields (JTextField), and text areas (JTextArea).
- the drop-down lists (actors and genre -- actorsJCB and genreJCB JComboBox's) are editable,
  which allows the user to add new values into the displayed selection, as though they were
  a JTextField. Hitting the return (enter) key when the user completes an edit causes the
  actionPerformed method to be called (NewActor and NewGenre action commands).
- the clear buttons are also handled by the actionPerformed method with different action
  commands, each causing the respective combo box to be cleared. Hitting return in the
  actors combo box causes the new text to be added to the combo box, if its not already there.
  These actions will be useful setting up the actors and genre that would be done when adding
  a new movie to the library. The add function is not necessary in the C++ client, but is
  required in the final assignment where the Java client is constructed from this GUI.
- selecting a terminal node in the tree causes the selected node to be added to the genre
  combo box. It also causes the text of the selected node to be appended to the plot text area.
- selecting a non-terminal node also causes the plot text area to be cleared of all selections.
- the tree is built using two text arrays one containing the movie title and the other containing
  the primary genre of the movie.
- selecting a menu item causes the actionPerformed method to be called where the appropriate
  action can be initiated.
- the constructor does the "wire-ups" adding the SampleMovieClient object to be added as a
  listener for button clicks, menu selections, tree selections, and edit actions in the
  combo boxes. If you want to handle tree node expansions (commonly done to build the sub-tree
  nodes, but not necessary here), there is a commented listener call in the constructor, and
  sample handler methods.
- playing a video is done by the underlying GUI class by calling a method. See the example of
  handling the Play menu item, and the javadocs for the GUI.

The C++ program uses the library package FLTK (full light toolkit). Setting up
this library on Debian Linux can be done with:
sudo apt-get install libfltk1.3-dev

The source code for both the view (src/cpp/MovieClientGui.cpp) and for the
controller (src/cpp/MovieClient.cpp) are included in this project.

The javadocs for the view class MovieClientGui.cpp can be found by opening
doc/cpp/html/index.html

Build the C++ version with:
ant build.cpp

and execute the C++ version with:
./bin/sampleMovieClient aName host port
such as:
./bin/sampleMovieClient TimLindquist 192.168.2.2 8888

Read the source code, both C++ and Java. The only menu functions implemented
in the source code are File-->Exit and Movie-->Play
Both clients are written to play the included music file (mp3) and video file (mp4).
Neither of the video files are protected, and their distribution does not violate any
copyright laws. In my defense, that was the primary criteria in their selection.

These sample clients are to provide Ser321 students with the basis for constructing their
solutions for the C++ movie player and browser and for the Java movie player and browser.

Movie Playback.
Currently movie playback functionality is not required for the distributed Movie Library
application. However, both sample clients (Java and C++) and this example are setup
to provide playback through the use of a Sun Microsystems distributed simple web server
(intended for downloading class files for RMI and serialization). You can execute
the web server with:
ant execute.streamer
or from the command line with:
java -jar lib/tools.jar -port 8888 -dir MediaFiles -trees -verbose

Access the media file using this web server with:
http://localhost:8888/MachuPicchuTimelapseVimeo.mp4

Once this simple web server is running, both clients Play menu item should work. The
C++ version assumes that VLC is installed (sudo apt-get install vlc).

end
