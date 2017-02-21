package ser321.media;

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
 * Copyright (c) 2015 Tim Lindquist,
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
 * of the MediaLibraryGui class. It is intended to be used in creating student
 * solutions to ser321 media playback and browser assignments.
 * This problem provides for browsing and managing information about
 * media files. It uses a Swing JTree, and JTextField controls to 
 * realize a GUI with a split pane. The left pane contains an expandable
 * JTree of the media library.
 * This file provides the controler for the client.
 *
 * This software is meant to run on Debian Wheezy Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @date    July, 2015
 **/
public class SampleMediaClient extends MediaLibraryGui implements
                                                       TreeWillExpandListener,
     					               ActionListener,
					               TreeSelectionListener {

   private static final boolean debugOn = true;
   private static final boolean bootstrapOn = false;
   private boolean stopPlaying;         //shared with playing thread.


   public SampleMediaClient(String author) {
      super(author);
      stopPlaying = false;
      if(bootstrapOn){
         System.out.println("bootstraping a single media description ...");
      }

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
    * buildInitialTree is called by MediaLibraryGui to initialize the JTree.
    * Classes that extend MediaLibraryGui should override this method to 
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
      String[] musicList = {"Come Monday","Fins","Crazy"};
      String[] musicAlbum = {"Greatest Hits","Greatest Hits","Single"};
      String[] videoList = {"Minions Banana Song","Minions Banana"};
      String[] videoGenre = {"Animation","Animation"};
      tree.removeTreeSelectionListener(this);
      DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
      DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
      clearTree(root, model);
      DefaultMutableTreeNode musicNode = new DefaultMutableTreeNode("Music");
      model.insertNodeInto(musicNode, root, model.getChildCount(root));
      DefaultMutableTreeNode videoNode = new DefaultMutableTreeNode("Video");
      model.insertNodeInto(videoNode, root, model.getChildCount(root));
      // put nodes in the tree for all  registered with the library
      for (int i = 0; i<musicList.length; i++){
         String aMTitle = musicList[i];
         String anAlbum = musicAlbum[i];
         DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(aMTitle);
         DefaultMutableTreeNode subNode = getSubLabelled(musicNode,anAlbum);
         if(subNode!=null){ // if album subnode already exists
            debug("album exists: "+anAlbum);
            model.insertNodeInto(toAdd, subNode,
                                 model.getChildCount(subNode));
         }else{ // album node does not exist
            DefaultMutableTreeNode anAlbumNode =
               new DefaultMutableTreeNode(anAlbum);
            model.insertNodeInto(anAlbumNode, musicNode,
                                 model.getChildCount(musicNode));
            DefaultMutableTreeNode aSubCatNode = 
               new DefaultMutableTreeNode("aSubCat");
            debug("adding subcat labelled: "+"aSubCat");
            model.insertNodeInto(toAdd,anAlbumNode,
                                 model.getChildCount(anAlbumNode));
         }
      }
      // put nodes in the tree for all video registered with the library
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
               titleJTF.setText(nodeLabel);
               typeJCB.setSelectedIndex(1);
            }
         }
      }catch (Exception ex){
         ex.printStackTrace();
      }
      tree.addTreeSelectionListener(this);
   }

   public void actionPerformed(ActionEvent e) {
      tree.removeTreeSelectionListener(this);
      if(e.getActionCommand().equals("Exit")) {
         System.exit(0);
      }else if(e.getActionCommand().equals("Restore")) {
         rebuildTree();
         System.out.println("Restore "+((true)?"successful":"unsuccessful"));
      }else if(e.getActionCommand().equals("Tree Refresh")) {
         rebuildTree();
      }else if(e.getActionCommand().equals("Play")){
         try{
            String nodeLabel = ((typeJCB.getSelectedIndex()==0)?
                                "Swan Song":
                                "Machu Picchu Time Lapse");
            titleJTF.setText(nodeLabel);
            String aURIPath = "file://"+System.getProperty("user.dir")+
               ((typeJCB.getSelectedIndex()==0)?"/PaperNavySwanSong.mp3":
                "/MachuPicchuTimelapseVimeo.mp4");
            playMedia(aURIPath, nodeLabel);
         }catch(Exception ex){
            System.out.println("Execption trying to play media:");
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
         String authorName = "Dr Lindquists Library";
         if(args.length >=1){
            authorName = args[0];
         }
         System.out.println("calling constructor name "+authorName);
         SampleMediaClient mla = new SampleMediaClient(authorName);
      }catch (Exception ex){
         System.out.println("Exception in main: "+ex.getMessage());
         ex.printStackTrace();
      }
   }
}
