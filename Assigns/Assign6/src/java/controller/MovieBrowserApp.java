package controller;

import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.beans.*;
import java.net.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.lang.Runtime;

public class MovieBrowserApp extends MovieLibraryGui implements
													TreeWillExpandListener,
													ActionListener,
													TreeSelectionListener {

	private boolean stopPlaying;         //shared with playing thread.
   private String host,port;
   
   public MovieBrowserApp(String author, String host, String port) {
   	super(author);
   	this.host = host;
   	this.port = port;
   	stopPlaying = false;
   	
   }

	/**
    * create and initialize nodes in the JTree of the left pane.
    * buildInitialTree is called by MovieLibraryGui to initialize the JTree.
    * Classes that extend MovieLibraryGui should override this method to 
    * perform initialization actions specific to the extended class.
    * The default functionality is to set base as the label of root.
    * In your solution, you will probably want to initialize by deserializing
    * your library and displaying the categories and subcategories in the
    * tree.
    * @param root Is the root node of the tree to be initialized.
    * @param base Is the string that is the root node of the tree.
    */
   public void buildInitialTree(DefaultMutableTreeNode root, String base){
      //set up the context and base name
      try{
         root.setUserObject(base);
      }catch (Exception ex){
         JOptionPane.showMessageDialog(this,"exception initial tree:"+ex);
         ex.printStackTrace();
      }
   }
   
   public static void main(String args[]) {
      try{
         String authorName = (args.length>=1)?args[0]:"Robert Beermans Library";
         String aHost = (args.length>=2)?args[1]:"127.0.0.1";
         String aPort = (args.length>=3)?args[2]:"8888";
         System.out.println("calling constructor name " + authorName);
         MovieBrowserApp mba = new SampleMovieClient(authorName, aHost, aPort);
      }catch (Exception ex){
         System.out.println("Exception in main: "+ex.getMessage());
         ex.printStackTrace();
      }
   }							
}
