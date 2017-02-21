package ser321.movie;

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
 * Purpose: This class provides a simple controller demonstrating the use
 * of the MovieLibraryGui class. It is intended to be used in creating student
 * solutions to ser321 movie playback and browser assignments.
 * This problem provides for browsing and managing information about
 * video files. It uses a Swing JTree, and JTextField controls to 
 * realize a GUI with a split pane. The left pane contains an expandable
 * JTree of the movie library.
 * This file provides the controler for the client.
 *
 * This software is meant to run on Debian Wheezy Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @date    January, 2017
 **/
public class SampleMovieClient extends MovieLibraryGui implements
                                                       TreeWillExpandListener,
     					               ActionListener,
					               TreeSelectionListener {

   private static final boolean debugOn = true;
   private static final boolean bootstrapOn = false;
   private boolean stopPlaying;         //shared with playing thread.
   private String host,port;

   public SampleMovieClient(String author, String aHost, String aPort) {
      super(author);
      this.host = aHost;
      this.port = aPort;
      stopPlaying = false;
      plotJTA.setText("You selected: ");

      actorsJCB.addActionListener(this);
      actorsJCB.setActionCommand("NewActor");
      actorsClearButton.addActionListener(this);
      actorsClearButton.setActionCommand("ClearActors");

      genreJCB.addActionListener(this);
      genreJCB.setActionCommand("NewGenre");
      genreClearButton.addActionListener(this);
      genreClearButton.setActionCommand("ClearGenre");

      for(int i=0; i<userMenuItems.length; i++){
         for(int j=0; j<userMenuItems[i].length; j++){
            userMenuItems[i][j].addActionListener(this);
         }
      }
      //tree.addTreeWillExpandListener(this);
      try{
         tree.addTreeSelectionListener(this);
         rebuildTree();
      }catch (Exception ex){
         JOptionPane.showMessageDialog(this,"Handling "+
                                " constructor exception: " + ex.getMessage());
      }
      setVisible(true);
   }

   private void debug(String message) {
      if (debugOn)
         System.out.println("debug: "+message);
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

   public void rebuildTree(){
      String[] videoList = {"The Force Awakens","2012","Race","The Internship","Annie","My Old Lady"};
      String[] videoGenre = {"Action","Action","Biography","Comedy","Comedy","Comedy"};
      tree.removeTreeSelectionListener(this);
      DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
      DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
      clearTree(root, model);
      DefaultMutableTreeNode videoNode = new DefaultMutableTreeNode("Video");
      model.insertNodeInto(videoNode, root, model.getChildCount(root));
      // put nodes in the tree for all video
      for (int i = 0; i<videoList.length; i++){
         String aTitle = videoList[i];
         String aGenre = videoGenre[i];
         DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(aTitle);
         DefaultMutableTreeNode subNode = getSubLabelled(videoNode,aGenre);
         if(subNode!=null){ // if album subnode already exists
            model.insertNodeInto(toAdd, subNode, model.getChildCount(subNode));
         }else{ // album node does not exist
            DefaultMutableTreeNode anAlbumNode =
               new DefaultMutableTreeNode(aGenre);
            model.insertNodeInto(anAlbumNode, videoNode,
                                 model.getChildCount(videoNode));
            DefaultMutableTreeNode aSubCatNode = 
               new DefaultMutableTreeNode("aSubCat");
            debug("adding subcat labelled: "+"aSubCat");
            model.insertNodeInto(toAdd,anAlbumNode,
                                 model.getChildCount(anAlbumNode));
         }
      }
      // expand all the nodes in the JTree
      for(int r =0; r < tree.getRowCount(); r++){
         tree.expandRow(r);
      }
      tree.addTreeSelectionListener(this);
   }

   private void clearTree(DefaultMutableTreeNode root, DefaultTreeModel model){
      try{
         DefaultMutableTreeNode next = null;
         int subs = model.getChildCount(root);
         for(int k=subs-1; k>=0; k--){
            next = (DefaultMutableTreeNode)model.getChild(root,k);
            debug("removing node labelled:"+(String)next.getUserObject());
            model.removeNodeFromParent(next);
         }
      }catch (Exception ex) {
         System.out.println("Exception while trying to clear tree:");
         ex.printStackTrace();
      }
   }

   private DefaultMutableTreeNode getSubLabelled(DefaultMutableTreeNode root,
                                                 String label){
      DefaultMutableTreeNode ret = null;
      DefaultMutableTreeNode next = null;
      boolean found = false;
      for(Enumeration e = root.children(); e.hasMoreElements();){
         next = (DefaultMutableTreeNode)e.nextElement();
         debug("sub with label: "+(String)next.getUserObject());
         if (((String)next.getUserObject()).equals(label)){
            debug("found sub with label: "+label);
            found = true;
            break;
         }
      }
      if(found)
         ret = next;
      else
         ret = null;
      return ret;
   }

   public void treeWillCollapse(TreeExpansionEvent tee) {
      debug("In treeWillCollapse with path: "+tee.getPath());
      tree.setSelectionPath(tee.getPath());
   }

   public void treeWillExpand(TreeExpansionEvent tee) {
      debug("In treeWillExpand with path: "+tee.getPath());
      //DefaultMutableTreeNode dmtn =
      //    (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
      //System.out.println("will expand node: "+dmtn.getUserObject()+
      //		   " whose path is: "+tee.getPath());
   }

   public void valueChanged(TreeSelectionEvent e) {
      try{
         tree.removeTreeSelectionListener(this);
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)
            tree.getLastSelectedPathComponent();
         if(node!=null){
            String nodeLabel = (String)node.getUserObject();
            debug("In valueChanged. Selected node labelled: "+nodeLabel);
            // is this a terminal node?
            if(node.getChildCount()==0 &&
               (node != (DefaultMutableTreeNode)tree.getModel().getRoot())){
               plotJTA.append(nodeLabel+", ");
               titleJTF.setText(nodeLabel);
               if (!contains(genreJCB, nodeLabel)){
                  genreJCB.removeActionListener(this);
                  genreJCB.addItem(nodeLabel);
                  int i = 0;
                  while(i<genreJCB.getItemCount()&&
                        !((String)genreJCB.getItemAt(i)).equals(nodeLabel)){
                     i++;
                  }
                  genreJCB.setSelectedIndex(i);
                  genreJCB.addActionListener(this);
               }
            }else{
               plotJTA.setText("You selected: ");
            }
         }
      }catch (Exception ex){
         ex.printStackTrace();
      }
      tree.addTreeSelectionListener(this);
   }

   // a method to determine whether a string is already in the combo box
   private boolean contains(JComboBox jcb, String text){
      boolean ret = false;
      for (int i=0; i< jcb.getItemCount(); i++){
         if (((String)jcb.getItemAt(i)).equals(text)){
            ret = true;
         }
      }
      return ret;
   }
   
   public void actionPerformed(ActionEvent e) {
      tree.removeTreeSelectionListener(this);
      if(e.getActionCommand().equals("Exit")) {
         System.exit(0);
      }else if(e.getActionCommand().equals("NewActor")) {
         debug("new actor selected "+(String)actorsJCB.getSelectedItem());
         if(!contains(actorsJCB,(String)actorsJCB.getSelectedItem())){
            actorsJCB.addItem((String)actorsJCB.getSelectedItem());
         }
         releasedJTF.setText((String)actorsJCB.getSelectedItem());
      }else if(e.getActionCommand().equals("ClearActors")) {
         actorsJCB.removeActionListener(this);
         actorsJCB.removeAllItems();
         actorsJCB.addActionListener(this);
      }else if(e.getActionCommand().equals("ClearGenre")) {
         genreJCB.removeActionListener(this);
         genreJCB.removeAllItems();
         genreJCB.addActionListener(this);
      }else if(e.getActionCommand().equals("Save")) {
         plotJTA.append("Save, ");
         rebuildTree();
         // what to do with boolean returns by server methods
         debug("Save "+((true)?"successful":"unsuccessful"));
      }else if(e.getActionCommand().equals("Restore")) {
         plotJTA.append("Restore, ");
      }else if(e.getActionCommand().equals("Tree Refresh")) {
         plotJTA.append("Tree Refresh, ");
         rebuildTree(); // contact the server to obtain all current titles then rebuild
      }else if(e.getActionCommand().equals("Add")) {
         plotJTA.append("Add, ");
      }else if(e.getActionCommand().equals("Remove")) {
         plotJTA.append("Remove, ");
      }else if(e.getActionCommand().equals("Play")){
         plotJTA.append("Play, ");
         try{
            String nodeLabel = "Machu Picchu Time Lapse";
            titleJTF.setText(nodeLabel);
            //String aURIPath = "file://"+System.getProperty("user.dir")+
            //                  "/MachuPicchuTimelapseVimeo.mp4";
            String aURIPath = "http://"+host+":"+port+
                              "/MachuPicchuTimelapseVimeo.mp4";
            playMovie(aURIPath, nodeLabel);
         }catch(Exception ex){
            System.out.println("Execption trying to play movie:");
            ex.printStackTrace();
         }
      }
      tree.addTreeSelectionListener(this);
   }

   public boolean sezToStop(){
      return stopPlaying;
   }

   public static void main(String args[]) {
      try{
         String authorName = (args.length>=1)?args[0]:"Dr Lindquists Library";
         String aHost = (args.length>=2)?args[1]:"127.0.0.1";
         String aPort = (args.length>=3)?args[2]:"8888";
         System.out.println("calling constructor name "+authorName);
         SampleMovieClient mla = new SampleMovieClient(authorName,aHost,aPort);
      }catch (Exception ex){
         System.out.println("Exception in main: "+ex.getMessage());
         ex.printStackTrace();
      }
   }
}
