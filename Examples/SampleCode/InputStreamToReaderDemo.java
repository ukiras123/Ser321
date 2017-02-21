import java.io.*;

// from Java Network Programming and Distributed Computing (Reilly)
public class InputStreamToReaderDemo {
	public static void main(String args[]) {
		try {
			System.out.print("Please enter your name : ");
			
			// Get the input stream representing standard input
			InputStream input = System.in;
			
			// Create an InputStreamReader
			InputStreamReader reader = new InputStreamReader(input);
			
			// Connect to a buffered reader, to use the readLine() method
			BufferedReader bufReader = new BufferedReader(reader);
			
			String name = bufReader.readLine();
			
			System.out.println("Pleased to meet you, " + name);
		} catch(IOException ioe) {
			System.err.println("I/O error : " + ioe);
		}
	}
}
