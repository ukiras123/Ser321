package ser321.sockets;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Copyright 2016 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * A class to demonstrate Java's support for http connections with
 * java.net.URLConnection
 * This program takes a url as command line input, and places the result of
 * the corresponding URL Post to the standard output stream.
 *
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version December, 2016
 */
public class FetchAPage {

   public static void main (String args[]) {
      try{
         String aUrlStr = (args.length > 0) ? args[0] : "http://pooh.poly.asu.edu/Ser321";
         BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
         while(!aUrlStr.equalsIgnoreCase("end")) {
            URL aUrl = new URL(aUrlStr);
            URLConnection uc = aUrl.openConnection();
            BufferedReader in = new BufferedReader(
               new InputStreamReader(uc.getInputStream()));
            String inLine;
            StringBuffer sb = new StringBuffer();
            while((inLine = in.readLine()) != null){
               sb.append(inLine);
            }
            in.close();
            System.out.println("The URL: "+aUrlStr+" Contains:\n"+sb.toString());
            System.out.print("Enter end or a URL >");
            aUrlStr = stdin.readLine();
         }
      }catch (Exception e){
         System.out.println(e.getMessage());
      }
   }
}

