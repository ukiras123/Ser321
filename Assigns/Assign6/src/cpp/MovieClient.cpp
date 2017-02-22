#include "MovieClientGui.cpp"

#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Output.H>
#include <FL/Fl_Text_Display.H>
#include <FL/Fl_Input_Choice.H>
#include <FL/Fl_Multiline_Input.H>
#include <FL/Fl_Tree.H>
#include <stdio.h>
#include <iostream>
#include <thread>
#include <stdlib.h>
#include <string>
#include <sstream>
#include <array>

/**
 * Copyright (c) 2017 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p/>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p/>
 * Please review the GNU General Public License at:
 * http://www.gnu.org/licenses/gpl-2.0.html
 * see also: https://www.gnu.org/licenses/gpl-faq.html
 * so you are aware of the terms and your rights with regard to this software.
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p/>
 * Purpose: C++ FLTK client sample.
 * This class extends the Gui component class MovieClientGui.
 * The class provides control functionality that respond to button clicks and tree item
 * selections.
 * This software is meant to run on Debian Jessie Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @file    MovieClient.cpp
 * @date    January, 2017
 **/

// string variable and run method for use with threading. A separate thread
// is created to run the vlc application, which plays the video file.
std::string cmd;
void run(){
   std::system(cmd.c_str());
}

class MovieClient : public MovieClientGui {

public:

   // Don't make the client stub for the json-rpc-cpp an instance variable of
   // this object. In FLTK, callbacks may be handled on different threads, and
   // because of the way libjson-rpc-cpp is implmented, you must recreate
   // the stub object in each callback method in which its used.
   std::string appAuthor;
   std::thread * playThread;
   std::string host;
   std::string port;

   /**
    * Constructor for the MovieClient class.
    * This constructor creates the callback for clicking the X (exit) in the window,
    * creates callback for all menu item selections, and creates a callback
    * for tree selection events. Event callbacks must be static methods, so the
    * object passed to the callback is the MovieClient object. The static callback
    * methods then call an instance method on the instance. For example,
    * a tree item selection calls the TreeCallbackS static method, passing it
    * the MovieClient instance (this) as an argument. The static TreeCallbackS uses
    * the instance to call the instance method which has access to the MovieClient,
    * and MovieClientGui instance variables (types Fl_Tree, FL_Input, and FL_Choice)
    * necessary to implement the control functionality.
    **/
   MovieClient(std::string name, std::string aHost, std::string aPort) : MovieClientGui(name.c_str()) {
      callback(ClickedX, (void*)this);
      playThread = NULL;
      menubar->callback(Menu_ClickedS, (void*)this);
      appAuthor = name;
      host = aHost;
      port = aPort;
      buildTree();
      tree->callback(TreeCallbackS, (void*)this);
   }

   /**
    * ClickedX is one of the callbacks for GUI controls. As partly described above,
    * callbacks in FLTK need to be static functions. But, static functions
    * cannot directly access instance data. This program uses "userdata"
    * to get around that by passing the instance to the callback
    * function. The callback then accesses whatever GUI control object
    * that it needs for implementing its functionality.
    */
   static void ClickedX(Fl_Widget * w, void * userdata) {
      std::cout << "You clicked Exit" << std::endl;
      MovieClient *o = (MovieClient*)userdata;
      if(o->playThread != NULL && (o->playThread)->joinable()){
         (o->playThread)->join();
      }
      exit(1);
   }

   // Static tree callback method
   static void TreeCallbackS(Fl_Widget*w, void*data) {
      MovieClient *o = (MovieClient*)data;
      o->TreeCallback(); //call the instance callback method
   }

   /**
    * TreeCallback is a callback for tree selections, deselections, expand or
    * collapse.
    */
   void TreeCallback() {
      // Find item that was clicked
      Fl_Tree_Item *item = (Fl_Tree_Item*)tree->item_clicked();
      std::cout << "Tree callback. Current selection is: ";
      if ( item ) {
         std::cout << item->label();
      } else {
         std::cout << "none";
      }
      std::cout << endl;
      std::string aStr("unknown");
      std::string aTitle(item->label());
      switch ( tree->callback_reason() ) {  // reason callback was invoked
        case       FL_TREE_REASON_NONE: {aStr = "none"; break;}
        case     FL_TREE_REASON_OPENED: {aStr = "opened";break;}
        case     FL_TREE_REASON_CLOSED: {aStr = "closed"; break;}
        case   FL_TREE_REASON_SELECTED: {
           aStr = "selection";
           titleInput->value(aTitle.c_str());
           break;
        }
        case FL_TREE_REASON_DESELECTED: {aStr = "deselected"; break;}
      default: {break;}
      }
   std::cout << "Callback reason: " << aStr.c_str() << endl;
   }

   // Static menu callback method
   static void Menu_ClickedS(Fl_Widget*w, void*data) {
      MovieClient *o = (MovieClient*)data;
      o->Menu_Clicked(); //call the instance callback method
   }

   // Menu selection instance method that has ccess to instance vars.
   void Menu_Clicked() {
      char picked[80];
      menubar->item_pathname(picked, sizeof(picked)-1);
      string selectPath(picked);
      cout << "Selected Menu Path: " << selectPath << endl;
      // get a stub to the library server
      // Handle menu selections calling methods on the stub where necessary.
      if(selectPath.compare("File/Save")==0){
         cout << "Menu item File/Save selected." << endl;
      }else if(selectPath.compare("File/Restore")==0){
         cout << "Menu item File/Restore selected." << endl;
      }else if(selectPath.compare("File/Exit")==0){
         cout << "Menu item File/Exit selected." << endl;
         if(playThread != NULL && playThread->joinable()){
            playThread->join();
         }
         exit(0);
      }else if(selectPath.compare("Movie/Remove")==0){
         cout << "Menu item Movie/Remove selected." << endl;
         cout << "Removing video with title: " << titleInput->value()
              << " " << endl;
         bool addResult = true;
         cout << "Remove " << ((addResult)?"successful":"unsuccessful") << endl;
      }else if(selectPath.compare("Movie/Play")==0){
         // uname returns OS Name. This program defined to work with
         // Linux and Darwin (Darwin is Mac OSX)
         std::string unameres = exec("uname");
         // pwd is print working directory. Used to build an absolute path to the
         // video file to be played.
         //std::string pwdPath = exec("pwd");
         //pwdPath = pwdPath.substr(0,pwdPath.length()-1);
         //std::cout << "OS type is: " << unameres << " curr.dir is: "
         //          << pwdPath << std::endl;
         // This path is only valid on linux so we will have to check ostype
         std::string pwdPath = "http://";
         std::stringstream streamLinux;
         streamLinux << "/usr/bin/vlc "
                     << pwdPath << host << ":" << port << "/"
                     << "MachuPicchuTimelapseVimeo.mp4";
         std::string aStr("Linux");
         std::stringstream streamMac;
         streamMac << "/Applications/VLC.app/Contents/MacOS/VLC "
                   << pwdPath << host << ":" << port << "/"
                     << "MachuPicchuTimelapseVimeo.mp4";
         cout << "mac command: " << streamMac.str() << endl;
         cout << "linux command: " << streamLinux.str() << endl;
         // start vlc to play the video file.
         // limit the comparison to the length of Linux to remove new line char.
         // Create a new thread to play the movie using vlc on the appropriate system.
         // An attempt to exit the GUI/Application will block to join
         // (syncronize) with the thread/vlc
         if(unameres.compare(0,aStr.length(),aStr)==0){
            string argLinux(streamLinux.str());
            cmd = argLinux;
            playThread = new std::thread(run);
         }else{
            string arg(streamMac.str());
            cmd = arg;
            playThread = new std::thread(run);
         }
      }
   }

   // local method to execute a system command (uname or pwd). Note, the
   // command is executed on the main thread.
   std::string exec(const char* cmd) {
      FILE* pipe = popen(cmd, "r");
      if (!pipe) return "ERROR";
      char buffer[128];
      std::string result = "";
      while(!feof(pipe)) {
         if(fgets(buffer, 128, pipe) != NULL)
            result += buffer;
      }
      pclose(pipe);
      return result;
   }

   // local method to build the tree in the GUI left panel.
   void buildTree(){
      std::array<std::string,6> videoList =
         {"The Force Awakens","2012","Race","The Internship","Annie","My Old Lady"};
      std::array<std::string,6> videoGenre =
         {"Action","Action","Biography","Comedy","Comedy","Comedy"};
      tree->clear();
      cout << endl << "Adding tree nodes for video titles: ";
      for(int i=0; i<videoList.size(); i++){
         cout << " " << videoList[i] << ", ";
         string title = videoList[i];
         string genre = videoGenre[i];
         std::stringstream stream;
         stream << "Video"
                << "/"
                << genre
                << "/" << title;
         tree->add(stream.str().c_str());
      }
      cout << endl;
      tree->root_label(appAuthor.c_str());
      tree->redraw();
   }

};

// main method for this program.
int main(int argc, char * argv[]) {
   std::string nameStr = (argc>1)?argv[1]:"Lindquist Library";
   std::string host = (argc>2)?argv[2]:"127.0.0.1";
   std::string port = (argc>3)?argv[3]:"8888";
   MovieClient mc(nameStr,host,port);
   return (Fl::run());
}
