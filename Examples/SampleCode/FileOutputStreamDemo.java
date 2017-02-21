import java.io.*;

// from Java Network Programming and Distributed Computing (Reilly)
public class FileOutputStreamDemo {
	public static void main(String args[]) {
		// Two parameters are required, the source and destination
		if (args.length != 2) {
			System.err.println("Syntax - FileOutputStreamDemo src dest");
			return;
		}
		
		String source = args[0];
		String destination = args[1];
		
		try {
			// Open source file for input
			InputStream input = new FileInputStream(source);
			
			System.out.println("Opened " + source + " for reading.");
			
			// Output file for output
			OutputStream output = new FileOutputStream(destination);
			
			System.out.println("Opened " + destination + " for writing.");
			
			int data = input.read();
			
			while (data != -1) {
				// Write byte of data to our file
				output.write(data);
				
				// Read next byte
				data = input.read();
			}
			
			// Close both streams
			input.close();
			output.close();
			
			System.out.println("I/O streams closed");
		} catch(IOException ioe) {
			System.err.println("I/O error - " + ioe);
		}
	}
}
