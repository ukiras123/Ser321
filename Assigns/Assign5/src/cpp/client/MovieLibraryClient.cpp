#include "MovieClientGui.cpp"

#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Output.H>
#include <FL/Fl_Text_Display.H>
#include <FL/Fl_Input_Choice.H>
#include <FL/Fl_Tree.H>
#include <Fl/Fl_Text_Editor.H>
#include <jsonrpccpp/client/connectors/httpclient.h>
#include <json/json.h>
#include "movielibrarystub.h"
#include <stdio.h>
#include <iostream>
#include <thread>
#include <stdlib.h>
#include <string>
#include <sstream>
#include <array>

using namespace jsonrpc;
using namespace std;

/**
 * Copyright (c) 2017 Robert Beerman,
 *
 * The author grants to the ASU Software Engineering program the right to copy
 * and execute this software for evaluation purposes only.
 * 
 * This class is a modified version of a sample provided by
 * Timothy Lindquist. Used by permission under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Purpose: This class is an implementation of a MovileLibrary GUI client developed for
 * Assignment 5 of Ser321
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 02/15/2017
 **/

class MovieLibraryClient : public MovieClientGui {

public:

   // Don't make the client stub for the json-rpc-cpp an instance variable of
   // this object. In FLTK, callbacks may be handled on different threads, and
   // because of the way libjson-rpc-cpp is implmented, you must recreate
   // the stub in each callback method in which its used.
   std::string appAuthor;
   std::string host;
   std::string selectedTitle;

   /**
    * Constructor for the MovieLibraryClient class.
    * This constructor creates the callback for clicking the X (exit) in the window,
    * creates callback for all menu item selections, and creates a callback
    * for tree selection events. Event callbacks must be static methods, so the
    * object passed to the callback is the MediaClient object. The static callback
    * methods then call an instance method on the instance. For example,
    * a tree item selection calls the TreeCallbackS static method, passing it
    * the MediaClient instance (this) as an argument. The static TreeCallbackS uses
    * the instance to call the instance method which has access to the MediaClient,
    * and MediaClientGui instance variables (Fl_Tree, FL_Input (title, artist, genre,
    * album), Fl_Choice (mediaType)) necessary to implement the control functionality.
    **/
   MovieLibraryClient(std::string name, std::string host) : MovieClientGui(name.c_str()) {
   	this->host = host;

      callback(ClickedX, (void*)this);
      menubar->callback(Menu_ClickedS, (void*)this);
      appAuthor = name;
      buildTree();
      tree->callback(TreeCallbackS, (void*)this);
   }

   /**
    * ClickedX is one of the callbacks for GUI controls.
    * Callbacks in FLTK need to be static functions. But, static functions
    * cannot directly access instance data. This program uses "userdata"
    * to get around that by passing the instance to the callback
    * function. The callback then accesses whatever GUI control object
    * that it needs for implementing its functionality.
    */
   static void ClickedX(Fl_Widget * w, void * userdata) {
      std::cout << "You clicked Exit" << std::endl;
      MovieLibraryClient *o = (MovieLibraryClient*)userdata;

      exit(1);
   }

   // Static tree callback method
   static void TreeCallbackS(Fl_Widget*w, void*data) {
      MovieLibraryClient *o = (MovieLibraryClient*)data;
      o->TreeCallback(); //call the instance callback method
   }

   /**
    * TreeCallback is a callback for tree selections, deselections, expand or
    * collapse.
    */
   void TreeCallback() {
   	HttpClient httpclient(host);
   	// get a stub to the library server
   	movielibrarystub mls(httpclient);
		
      // Find item that was clicked
      Fl_Tree_Item *item = (Fl_Tree_Item*)tree->item_clicked();
      selectedTitle = "";
      std::cout << "Tree callback. Current selection is: ";
      if ( item ) {
      	selectedTitle = item->label();
         std::cout << selectedTitle;
      } else {
         std::cout << "none";
      }
      std::cout << endl;
      std::string aStr("unknown");
      std::string aTitle(item->label());
      switch ( tree->callback_reason() ) {  // reason callback was invoked
        case FL_TREE_REASON_NONE: {aStr = "none"; break;}
        case FL_TREE_REASON_OPENED: {aStr = "opened";break;}
        case FL_TREE_REASON_CLOSED: {aStr = "closed"; break;}
        case FL_TREE_REASON_SELECTED: {
           aStr = "selection";
           Json::Value movieDescription = mls.get(selectedTitle);
           cout << movieDescription << endl;
           
           titleInput->value(aTitle.c_str());
           ratedInput->value(movieDescription.get("Rated", Json::Value("rated")).asCString());
           releasedInput->value(movieDescription.get("Released", Json::Value("released")).asCString());
           plotMLIn->value(movieDescription.get("Plot", Json::Value("plot")).asCString());
           runtimeInput->value(movieDescription.get("Runtime", Json::Value("runtime")).asCString());
           filenameInput->value(movieDescription.get("Filename", Json::Value("filename")).asCString());
           
           Json::Value actors = movieDescription.get("Actors", Json::Value("actors"));
           
           actorsChoice->clear();
           for (int i = 0; i < actors.size(); i++) {
           		actorsChoice->add(actors[i].asCString());
           }
           actorsChoice->value(0);
           
           Json::Value genres = movieDescription.get("Genre", Json::Value("genre description"));
           
           genreChoice->clear();
           for (int i = 0; i < genres.size(); i++) {
           		genreChoice->add(genres[i].asCString());
           }
           genreChoice->value(0);

           break;
        }
        case FL_TREE_REASON_DESELECTED: {aStr = "deselected"; break;}
      default: {break;}
      }
   std::cout << "Callback reason: " << aStr.c_str() << endl;
   }

   // Static menu callback method
   static void Menu_ClickedS(Fl_Widget*w, void*data) {
      MovieLibraryClient *o = (MovieLibraryClient*)data;
      o->Menu_Clicked(); //call the instance callback method
   }

   // Menu selection instance method that has access to instance vars.
   void Menu_Clicked() {
   	HttpClient httpclient(host);
   	
		// get a stub to the library server
   	movielibrarystub mls(httpclient);
		cout << "Connecting to host " << host << endl;
   
      char picked[80];
      menubar->item_pathname(picked, sizeof(picked)-1);
      string selectPath(picked);

      // Handle menu selections
      if (selectPath.compare("File/Save") == 0){
         cout << "Menu item Save selected." << endl;
      } else if (selectPath.compare("File/Restore") == 0){
         cout << "Menu item Restore selected." << endl;
         mls.restoreFromFile("movies.json");
         buildTree();
      } else if (selectPath.compare("File/Tree Refresh") == 0){
         cout << "Menu item Tree Refresh selected." << endl;
         buildTree();
      } else if (selectPath.compare("File/Exit") == 0){
         cout << "Menu item Exit selected." << endl;

         exit(0);
      } else if (selectPath.compare("Movie/Remove") == 0) {
      	cout << "Menu item Remove selected." << endl;

      	std::cout << "Tree callback. Removing: " << selectedTitle << endl;
      	
      	if (mls.remove(selectedTitle)) {
      		buildTree();
      	}
      }  
   }

   // local method to build the tree in the GUI left panel.
   void buildTree(){
   	HttpClient httpclient(host);
		// get a stub to the library server
   	movielibrarystub mls(httpclient);
		cout << "Connecting to host " << host << endl;
		
		tree->clear();
		Json::Value movieList = mls.getTitles();
		cout << movieList << endl;
		for (int i = 0; i < movieList.size(); i++) {
			string title = movieList[i].asString();
			std::stringstream stream;
			stream << "Video"
					 << "/"
					 << title;
			tree->add(stream.str().c_str());
		}
		
		tree->root_label(appAuthor.c_str());
      tree->redraw();

   }

};

// main method for this program.
int main(int argc, char * argv[]) {
   std::string nameStr = "Beerman Library";
   std::string host = (argc>1)?argv[1]:"127.0.0.1:8080";
   MovieLibraryClient mc(nameStr,host);
   return (Fl::run());
}
