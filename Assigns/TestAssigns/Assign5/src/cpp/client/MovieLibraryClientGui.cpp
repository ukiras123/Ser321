#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Output.H>
#include <FL/Fl_Tree.H>
#include <FL/Fl_Tree_Item.H>
#include <FL/Fl_Menu_Bar.H>
#include <FL/Fl_Choice.H>
#include <FL/Fl_Text_Display.H>
#include <FL/Fl_Text_Buffer.H>
#include <Fl/Fl_Text_Editor.H>
#include <stdio.h>
#include <iostream>
#include <stdlib.h>

using namespace std;

/**
 * \class MediaClientGui
 * Copyright (c) 2015 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p>
 * Please review the GNU General Public License at:
 * http://www.gnu.org/licenses/gpl-2.0.html
 * see also: https://www.gnu.org/licenses/gpl-faq.html
 * so you are aware of the terms and your rights with regard to this software.
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p>
 * Purpose: Sample C++ FLTK view class. MediaClientGui constructs the view
 * for media app. This class is extended by the client controller which is
 * the MediaClient class. MediaClient defines the call-backs for UI controls.
 * It contains sample control functions that respond to button clicks and tree
 * selects.
 * This software is meant to run on Debian Wheezy Linux
 * <p>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * \author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * \file    MediaClientGui.cpp
 * \date    August, 2015
 **/
class MovieLibraryClientGui : public Fl_Window {
protected:

   /**
    * tree is the Fl_Tree object that occupies the left side of the window.
    * This tree control provides the ability to select nodes. The app uses
    * this selection functionality to request
    * actions based on the selection, such as remove a media item, or play a media
    * file.
    */
   Fl_Tree * tree;

   /**
    * titleInput is the Fl_Input object labelled Title.
    * Its for the user to enter the media tile.
    * The controller places the title of the tree's currently selected media
    * in this field.
    */
   Fl_Input * titleInput;

   /**
    * albumInput is the Fl_Input object labelled Album.
    * Its for the user to enter the media album name. For videos, genre is
    * used to organize video. For music, album is used as the organization term.
    * The controller places the album of the tree's currently selected media
    * in this field.
    */
   Fl_Input * ratedInput;

   /**
    * releasedInput is the Fl_Input object labelled Released.
    * Its for the user to enter the artist for music, or for entering the
    * primary actor for videos.
    * The controller places the artist of the tree's currently selected media
    * in this field.
    */
   Fl_Input * releasedInput;

   /**
    * genreInput is the Fl_Input object labelled Genre.
    * Its for the user to display the genre for the movie.
    * The controller places the genre of the tree's currently selected movie
    * in this field.
    */
   Fl_Input * genreInput;

   /**
    * menubar is the Fl_Menu_Bar object containing all of the menuitems for the Gui.
    */
   Fl_Menu_Bar *menubar;

   Fl_Input *runtimeInput;
   Fl_Input *filenameInput;
   Fl_Input *actorsInput;
   Fl_Text_Buffer *plotBuffer;
   Fl_Text_Editor *plotEditor;

public:

   /** A Constructor for MovieLibraryClientGui
    * Constructor taking a c-string argument, which is used as a window label
    * for the client application.
    * @param name the c-string to be used as window title and root of tree
    **/
   MovieLibraryClientGui(const char * name = "Movie Library Browser") : Fl_Window(665,400,name) {
      begin();

      menubar = new Fl_Menu_Bar(0, 0, this->w(), 25);
      menubar->add("File/Save");
      menubar->add("File/Restore");
      menubar->add("File/Tree Refresh");
      menubar->add("File/Exit");
      menubar->add("Media/Remove");

      // create a tree control at position x=10, y=10. Its 150 pixels wide
      // and window height less 45 pixels high. Add some sample tree nodes.
      tree = new Fl_Tree(10, 35, 225, this->h()-45);
      tree->add("Test Movie");
      tree->root_label(name);

      /*
       * add a text input control at x=250, y=35 of width 200 pixels and
       * height of 25 pixels. Initialize it contents to media title.
       */
      titleInput = new Fl_Input(315, 35, 325, 25);
      titleInput->label("Title");
      titleInput->value("movie title");
		
      /*
       * add a text input control at x=250, y=35 of width 200 pixels and
       * height of 25 pixels. Initialize it contents to media title.
       */
      ratedInput = new Fl_Input(315, 70, 50, 25);
      ratedInput->label("Rated");
      ratedInput->value("rated");

      genreInput = new Fl_Input(315, 105, 325, 25);
      genreInput->label("Genre");
      genreInput->value("genre description");

      releasedInput = new Fl_Input(315, 140, 200, 25);
      releasedInput->label("Released");
      releasedInput->value("release date");
      
      runtimeInput = new Fl_Input(315, 175, 200, 25);
      runtimeInput->label("Runtime");
      runtimeInput->value("runtime");
      
      filenameInput = new Fl_Input(315, 210, 325, 25);
      filenameInput->label("Filename");
      filenameInput->value("filename");
      
      actorsInput = new Fl_Input(315, 245, 325, 25);
      actorsInput->label("Actors");
      actorsInput->value("actors");
           
      plotBuffer = new Fl_Text_Buffer();
      plotEditor = new Fl_Text_Editor(315, 290, 325, 100, "Plot");
      plotEditor->buffer(plotBuffer);
      plotBuffer->text("plot");
		plotEditor->wrap_mode(1, 0);
      
      end();
      show();
   }
};
