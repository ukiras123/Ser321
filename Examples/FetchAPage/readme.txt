Author: Tim Lindquist (Tim.Lindquist@asu.edu), ASU Polytechnic, CIDSE, SE
Version: December 2016

See http://pooh.poly.asu.edu/Ser321

Purpose: This program is to demonstrate the use of Java's java.net.URLConnection
class to fetch a url.

Building and running the program can be done in several ways:
ant execute
or
ant build
followed by any of the following ways to execute:
java -cp classes ser321.sockets.FetchAPage http://pooh.poly.asu.edu/Ser321
java -jar lib/fetchapage.jar "http://www.omdbapi.com/?t=Frozen&y=&plot=short&r=json"
// below works but may not display the user input prompt.
ant execute -Durl=http://pooh.poly.asu.edu/Ser502

To view the targets that are available in this project type ant (targets is the default
target).
1. ant
   The default target shows all targets supported, and the syntax for invoking programs
   after they are built.
   
2. To build 
   ant build

4. To clean
   ant clean

Learning outcomes:
1. Read the program and the java docs on java.net.URL and java.net.URLConnection
   to understand the mechanics of Java support for http connections, and the
   various options for controlling a URL connection with this base class for
   Java's access to URL's.
2. Read the build.xml file. You should be familiar with the following Ant constructs:
   properties and their use. Also look at command line above which sets a property value.
   Also, targets, javac and java tasks (elements). This build also introduces constructing
   an executable java archive. That is, a jar file that contains all the user-defined
   and auxilary classes needed to execute a program. Executable jars are started from
   the command line with a command similar to:
   java -jar lib/fetchapage.jar
   executable jars are a convenient way to distribute an application (and the cause
   of security concerns). Executable jar's must contain a manifest which indicates
   the class containing the main method.
end

