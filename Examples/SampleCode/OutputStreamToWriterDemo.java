import java.io.*;
import java.util.*;

// from Java Network Programming and Distributed Computing (Reilly)
public class SerializationDemo {
	public static void main(String args[]) {
		try {
			Vector list;
			
			// Create a buffered reader for easy input
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in));
			System.out.println("Checking for previous serialized list");
			
			// Check to see if serialized list exists
			try {
				FileInputStream fin = new FileInputStream("list.out");
				
				// Connect an object input stream to the list
				ObjectInputStream oin = new ObjectInputStream(fin);
				
				try {
					// Read the vector back from the list
					Object obj = oin.readObject();
					
					// Cast back to a vector
					list = (Vector) obj;
				} catch(ClassCastException cce) {
					// Can't read it, create a blank one
					list = new Vector();
				} catch(ClassNotFoundException cnfe) {
					// Can't read it, create a blank one
					list = new Vector();
				}
				
				fin.close();
			} catch(FileNotFoundException fnfe) {
				// Create a blank vector
				list = new Vector();
			}
			
			// Repeat indefinitely
			for (;;) {
				// Now, display menu
				System.out.println("Menu :-");
				System.out.println("1.. Add item");
				System.out.println("2.. Delete item");
				System.out.println("3.. List items");
				System.out.println("4.. Save and quit");
				System.out.println("Choice : ");
				
				// Read choice
				String response = reader.readLine();
				
				// Convert to an int
				int choice = Integer.parseInt(response);
				
				switch(choice) {
					case 1 :
						// Add the item to list
						System.out.print("Enter item : ");
						String item = reader.readLIne();
						list.addElement(item);
						break;
					case 2 :
						// Delete the item from list
						System.out.print("Enter item : ");
						String deadItem = reader.readLine();
						list.removeElement(deadItem);
						break;
					case 3 :
						// List the elements of the list
						for (Enumeration e = list.elements(); e.hasMoreElements();) {
							System.out.println(e.nextElement());
						}
						break;
					case 4 :
						// Save list and terminate
						System.out.println("Saving list");
						FileOutputStream fout = new FileOutputStream("list.out");
						
						// Construct an object output stream
						ObjectOutputStream oout = new ObjectOutputStream(fout);
						
						// Write the object to the stream
						oout.writeObject(list);
						fout.close();
						System.exit(0);
			
				}
			}
		} catch(IOException ioe) {
			System.err.println("I/O error : " + ioe);
		}
	}
}
