package cst502.objcXml;

import java.io.*;
import java.util.*;
import java.beans.*;

/**
 * A Java class defining information about a sailboat.
 * Sailboat objects are serializable to/from Xml and compatible with
 * Objective-C sailboat objects.
 * Cst502 Emerging Languages and Programming Technologies
 * see http://pooh.poly.asu.edu/Cst502
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic Engineering
 * @version December 2011
 */
public class Sailboat extends Object implements Serializable {

   public String model, name;
   public double beam, displacement, lengthWL;

   public Sailboat () {
      model=name="";
      beam=displacement=lengthWL=0.0;
   }

   public Sailboat(String model, String name, double beam,
                   double displacement, double lengthWL){
      this.model = model; 
      this.name = name; 
      this.beam = beam; 
      this.displacement = displacement; 
      this.lengthWL = lengthWL; 
   }

   /**
    * Construct a book instance from an input stream of xml
    * @param xmlIS The input stream of xml
    */
   public Sailboat(InputStream xmlIS){
      try{
         XMLDecoder decoder = new XMLDecoder(xmlIS);
         Sailboat abl = (Sailboat)decoder.readObject();
         decoder.close();
         this.model = abl.getModel();
         this.name = abl.getName();
         this.beam = abl.getBeam();
         this.displacement = abl.getDisplacement();
         this.lengthWL = abl.getLengthWL();
      }catch(Exception e){
         System.out.println("Sailboat stream constructor: ");
         e.printStackTrace();
      }
   }

   /**
    * Write the sailboat to an output stream as an Xml document.
    * @param xmlos The Xml output stream to which the xml document
    * is to be written.
    */
   public void save(OutputStream xmlos) {
      try {
         XMLEncoder encoder = new XMLEncoder(xmlos);
         encoder.writeObject(this);
         encoder.flush();
         encoder.close();
      }catch (Exception e){
         System.out.println("Exception while saving boat: "+e.getMessage());
      }
   }

   /**
    * Get the model of this sailboat.
    * @return The sailboat's model string.
    */
   public String getModel(){
      return model;
   }

   /**
    * Set the model of this sailboat.
    */
   public void setModel(String aModel){
      model = aModel;
   }

   /**
    * Get the name of this sailboat.
    * @return The sailboat's name string
    */
   public String getName(){
      return name;
   }

   /**
    * Set the name of this sailboat.
    */
   public void setName(String aName){
      name = aName;
   }

   /**
    * Get the beam of this sailboat.
    * @return The sailboat's beam string.
    */
   public double getBeam(){
      return beam;
   }

   /**
    * Set the  beam of this sailboat.
    */
   public void setBeam(double aBeam){
      beam = aBeam;
   }

   /**
    * Get the displacement of this sailboat.
    * @return The sailboat's displacement string.
    */
   public double getDisplacement(){
      return displacement;
   }

   /**
    * Set the displacement of this sailboat.
    */
   public void setDisplacement(double aDisplacement){
      displacement = aDisplacement;
   }

   /**
    * Get the lengthWL of this sailboat.
    * @return The sailboat's lengthWL string.
    */
   public double getLengthWL(){
      return lengthWL;
   }

   /**
    * Set the lengthWL of this sailboat.
    */
   public void setLengthWL(double aLengthWL){
      lengthWL = aLengthWL;
   }

   public double dOverL(){
      return((displacement/2240.0)/Math.pow((0.01*lengthWL),3.0));
   }

   public void print(){
      System.out.format("Sailboat "+name+" is an "+model+" with d/l %6.2f\n",
                         dOverL());
   }

   public static void main(String args[]) {
      try {
         Hashtable<String,Sailboat> env = new Hashtable<String,Sailboat>();
         Sailboat sb;
         BufferedReader stdin = new BufferedReader(
                                            new InputStreamReader(System.in));
         System.out.print("Enter one of the following:\n"+
                          "new model name beam disp lengthWL\n"+
                          "read name\n"+
                          "write name\n"+
                          "print name\n"+
                          "list\n"+
                          "end\n>");
         String inStr = stdin.readLine();
         StringTokenizer st = new StringTokenizer(inStr);
         String opn = st.nextToken();
         while(!opn.equalsIgnoreCase("end")) {
            if(opn.equalsIgnoreCase("new")){
               String model = st.nextToken();
               String name = st.nextToken();
               double beam = Double.valueOf(st.nextToken());
               double disp = Double.valueOf(st.nextToken());
               double length = Double.valueOf(st.nextToken());
               env.put(name,new Sailboat(model,name,beam,disp,length));
            }else if (opn.equalsIgnoreCase("read")){
               String name = st.nextToken();
               FileInputStream xmlis = new FileInputStream(name+".xml");
               sb = new Sailboat(xmlis);
               env.put(name,sb);
               sb.print();
            }else if (opn.equalsIgnoreCase("write")) {
               String name = st.nextToken();
               FileOutputStream xmlos = new FileOutputStream(name+".xml");
               sb = env.get(name);
               sb.save(xmlos);
               sb.print();
            }else if (opn.equalsIgnoreCase("list")) {
               System.out.print("Sailboats I know about are: ");
               for (Enumeration<String> e = env.keys(); e.hasMoreElements();)
                  System.out.print(e.nextElement()+" ");
               System.out.println();
            }else if (opn.equalsIgnoreCase("print")) {
               sb = env.get(st.nextToken());
               sb.print();
            }
            System.out.print(">");
            inStr = stdin.readLine();
            st = new StringTokenizer(inStr);
            opn = st.nextToken();
         }
      }catch (Exception e) {
        System.out.println("Oops, you didn't enter enough or the right stuff");
      }
   }
}
