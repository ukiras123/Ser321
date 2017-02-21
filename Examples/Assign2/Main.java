//import ser321.movie.MovieDescription;
//import ser321.movie.MovieLibrary;
//import ser321.movie.MovieLibraryImpl;
/**
 * Copyright (c) 2016 Tim Lindquist,
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
 * <p/>
 * Purpose: This class is part of an example developed for the instructor
 * solution of Assignment 2 of Ser321: MovieLibrary.
 *
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version 12/21/2016
 **/
public class Main {
   public static void main(String args[]) {
      try {
         MovieLibraryImpl myLib = new MovieLibraryImpl();
         MovieDescription puppy = new MovieDescription("Minions Puppy","NR","10 Dec 2013",
             "4:16 min","Dave seeing many owners walk their dogs wants a puppy of his own. "+
             "He finds a mini-UFO who becomes his pal. This short film released with Despic"+
             "able Me 2 chronicles how Dave helps the UFO return home.","MinionsPuppy.mp4",
             "Dave","Animation");
         puppy.addGenre("Family");
         puppy.addGenre("Cartoon");
         puppy.addActor("Gru");
         boolean test = myLib.add(puppy);
         System.out.println("Added: "+puppy.toString());
         MovieDescription bananaSong = new MovieDescription("Minions Banana Song","PG",
                                                            "12 Dec 2015","3 min",
             "Banana is a song sung by The Minions in the teaser trailer of Despicable Me 2. "+
             "It is a parody of the Beach Boys Barbara Ann. One minion gets annoyed by "+
             "another, most likely Stuart, who keeps on playing his party horn while they "+
             "are singing. So, at the end, he punches Stuart.","MinionsBananaSong.mp4",
             "Dave","Animation");
         bananaSong.addGenre("Family");
         bananaSong.addGenre("Cartoon");
         bananaSong.addActor("Gru");
         test = myLib.add(bananaSong);
         bananaSong = myLib.get("Minions Banana Song");
         System.out.println("Used library get to fetch: "+bananaSong.toString());
         System.out.println("After adding two movies, the library contains the titles: ");
         System.out.print("   ");
         String[] mTitles = myLib.getTitles();
         for(int i=0; i<mTitles.length; i++){
            System.out.print(mTitles[i]+((i==mTitles.length-1)?"":", "));
         }
         System.out.println();
         test = myLib.remove(bananaSong.getTitle());
         System.out.println("Movie titles after removing Minions Banana Song the titles are:");
         System.out.print("   ");
         mTitles = myLib.getTitles();
         StringBuffer sb = new StringBuffer();
         for (int i=0; i<mTitles.length; i++){
            sb.append(mTitles[i]+((i==mTitles.length-1)?"":", "));
            //sb.append(", ");
         }
         System.out.println(sb.toString());
      }catch (Exception e) {
         System.out.println("Oops, you didn't enter the right stuff");
      }
   }
}
