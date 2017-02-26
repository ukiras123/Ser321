package client;

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
import java.lang.reflect.*;

import org.json.JSONObject;
import org.json.JSONArray;

import ser321.movie.*;
import client.MovieLibraryStub;

public class MovieBrowserApp extends MovieLibraryGui implements
													TreeWillExpandListener,
													ActionListener,
													TreeSelectionListener {

	private boolean stopPlaying;         //shared with playing thread.
   private String host, port;
   private MovieLibraryStub movieLibrary;
   private boolean debugOn = true;
   
   public MovieBrowserApp(String author, String host, String port) {
   	super(author);
   	this.host = host;
   	this.port = port;
   	
   	movieLibrary = (MovieLibraryStub)new MovieLibraryStub(host, Integer.parseInt(port));
   	
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
      try {
      	JSONArray jsonVideoList = movieLibrary.getTitles();
      	String[] videoList = new String[jsonVideoList.length()];
      	
      	for (int i = 0; i < jsonVideoList.length(); i++) {
      		videoList[i] = jsonVideoList.get(i).toString();
      	}

		   tree.removeTreeSelectionListener(this);
		   DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		   DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		   clearTree(root, model);
		   DefaultMutableTreeNode videoNode = new DefaultMutableTreeNode("Movie");
		   model.insertNodeInto(videoNode, root, model.getChildCount(root));
		   
		   // put nodes in the tree for all video
		   for (int i = 0; i<videoList.length; i++){
		      String aTitle = videoList[i];
		      DefaultMutableTreeNode toAdd = new DefaultMutableTreeNode(aTitle);
		      
		      model.insertNodeInto(toAdd, videoNode, i);
		   }
		   
		   // expand all the nodes in the JTree
		   for(int r =0; r < tree.getRowCount(); r++){
		      tree.expandRow(r);
		   }
		   
		   tree.addTreeSelectionListener(this);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
   }

   private void clearTree(DefaultMutableTreeNode root, DefaultTreeModel model){
      try{
         DefaultMutableTreeNode next = null;
         int subs = model.getChildCount(root);
         for(int k=subs-1; k>=0; k--){
            next = (DefaultMutableTreeNode)model.getChild(root,k);
            model.removeNodeFromParent(next);
         }
      }catch (Exception ex) {
         System.out.println("Exception while trying to clear tree:");
         ex.printStackTrace();
      }
   }

   public void treeWillCollapse(TreeExpansionEvent tee) {
      debug("In treeWillCollapse with path: "+tee.getPath());
      tree.setSelectionPath(tee.getPath());
   }

   public void treeWillExpand(TreeExpansionEvent tee) {
      debug("In treeWillExpand with path: "+tee.getPath());
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
               
					JSONObject movie = movieLibrary.get(nodeLabel).getJSONObject("result");
               
               titleJTF.setText(nodeLabel);
               releasedJTF.setText(movie.getString("Released"));
               runtimeJTF.setText(movie.getString("Runtime"));
               ratedJTF.setText(movie.getString("Rated"));
               plotJTA.setText(movie.getString("Plot"));
               fileNameJTF.setText(movie.getString("Filename"));
               
               JSONArray jsonActorsArray = movie.getJSONArray("Actors");
               actorsJCB.removeActionListener(this);
         		actorsJCB.removeAllItems();
         
               for (int i = 0; i < jsonActorsArray.length(); i++) {
               	actorsJCB.addItem(jsonActorsArray.getString(i));
               }
               
               actorsJCB.addActionListener(this);
               
               JSONArray jsonGenreArray = movie.getJSONArray("Genre");
               genreJCB.removeActionListener(this);
         		genreJCB.removeAllItems();
         
               for (int i = 0; i < jsonGenreArray.length(); i++) {
               	genreJCB.addItem(jsonGenreArray.getString(i));
               }
               
               genreJCB.addActionListener(this);
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
      	File videoFile = new File(System.getProperty("user.dir") + "/MachuPicchuTimelapseVimeo.mp4");
      	if (videoFile.exists()) {
      		videoFile.delete();
      	}
         System.exit(0);
      }else if(e.getActionCommand().equals("NewActor")) {
         debug("new actor selected "+(String)actorsJCB.getSelectedItem());
         if(!contains(actorsJCB,(String)actorsJCB.getSelectedItem())){
            actorsJCB.addItem((String)actorsJCB.getSelectedItem());
         }
      }else if(e.getActionCommand().equals("NewGenre")) {
         debug("new genre selected "+(String)genreJCB.getSelectedItem());
         if(!contains(genreJCB,(String)genreJCB.getSelectedItem())){
            genreJCB.addItem((String)genreJCB.getSelectedItem());
         }
      }else if(e.getActionCommand().equals("ClearActors")) {
         actorsJCB.removeActionListener(this);
         actorsJCB.removeAllItems();
         actorsJCB.addActionListener(this);
      }else if(e.getActionCommand().equals("ClearGenre")) {
         genreJCB.removeActionListener(this);
         genreJCB.removeAllItems();
         genreJCB.addActionListener(this);
      }else if(e.getActionCommand().equals("Save")) {
         debug("Save function not implemented, per requirements");
      }else if(e.getActionCommand().equals("Restore")) {
         debug("Restore function not implemented, per requirements");
      }else if(e.getActionCommand().equals("Tree Refresh")) {
         rebuildTree(); // contact the server to obtain all current titles then rebuild
      }else if(e.getActionCommand().equals("Add")) {
	      JSONObject movieToAdd = new JSONObject();
	      
	      movieToAdd.put("Title", titleJTF.getText());
         movieToAdd.put("Runtime", runtimeJTF.getText());
         movieToAdd.put("Released", releasedJTF.getText());
         movieToAdd.put("Rated", ratedJTF.getText());
         movieToAdd.put("Plot", plotJTA.getText());
         movieToAdd.put("Filename", fileNameJTF.getText());
         
      	// build the JSONArray containing the actor list
      	String[] actorsArray = new String[actorsJCB.getItemCount()];
      	
      	for (int i = 0; i < actorsJCB.getItemCount(); i++) {
      		actorsArray[i] = actorsJCB.getItemAt(i);
      	}
      	
         JSONArray jsonActorsArray = new JSONArray(actorsArray);
         movieToAdd.put("Actors", jsonActorsArray);
         
         // build the JSONArray containing the genre list
         String[] genreArray = new String[genreJCB.getItemCount()];
      	
      	for (int i = 0; i < genreJCB.getItemCount(); i++) {
      		genreArray[i] = genreJCB.getItemAt(i);
      	}
      	
         JSONArray jsonGenreArray = new JSONArray(genreArray);
         movieToAdd.put("Genre", jsonGenreArray);
         
         if (movieLibrary.add(movieToAdd)) {
         	rebuildTree();
         }
      }else if(e.getActionCommand().equals("Remove")) {
         DefaultMutableTreeNode node =
         		(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
         
         if(node!=null){
            String nodeLabel = (String)node.getUserObject();
            if(node.getChildCount()==0 &&
               	(node != (DefaultMutableTreeNode)tree.getModel().getRoot())){
               
            	if(movieLibrary.remove(nodeLabel)) {
            		rebuildTree();
            	}
            }
         }
      }else if(e.getActionCommand().equals("Play")){
         Socket sock = null;
         
         try {
         	sock = new Socket(host, 2020);
         	ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
         	ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
         	int fileSize = 0;
         	int bytesRead = 0;

				
         	for (int i = 0; i < 2; i++) {
         		if (i == 0) {
         			os.writeObject("MachuPicchuTimelapseVimeo.mp4");
         			fileSize = is.readInt();
         		} else {
         			byte[] fileByteArray = new byte[fileSize];
         			BufferedOutputStream bos = new BufferedOutputStream(
         												new FileOutputStream(System.getProperty("user.dir") + "/MachuPicchuTimelapseVimeo.mp4"));
         			
         			while (bytesRead < fileSize) {
         				int result = is.read(fileByteArray, bytesRead, fileSize - bytesRead);
         				if (result == -1) break;
         				bytesRead += result;
         			}

         			bos.write(fileByteArray, 0, fileSize);
         			bos.flush();
         			bos.close();
         		}
         	}
         	
         	debug("File downloaded");
         	
            String nodeLabel = "Machu Picchu Time Lapse";
            titleJTF.setText(nodeLabel);
            String aURIPath = "file://"+System.getProperty("user.dir")+
                              "/MachuPicchuTimelapseVimeo.mp4";
            playMovie(aURIPath, nodeLabel);
            

         } catch(Exception ex){
            System.out.println("Exception trying to play movie:");
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
         String authorName = "Robert Beermans Library";
         String aHost = (args.length>=1)?args[0]:"127.0.0.1";
         String aPort = (args.length>=2)?args[1]:"8888";
         System.out.println("calling constructor name " + authorName);
         MovieBrowserApp mba = new MovieBrowserApp(authorName, aHost, aPort);
      }catch (Exception ex){
         System.out.println("Exception in main: "+ex.getMessage());
         ex.printStackTrace();
      }
   }							
}
