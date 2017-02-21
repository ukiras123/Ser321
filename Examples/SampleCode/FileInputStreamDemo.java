import java.io.*;

// from Java Network Programming and Distributed Computing (Reilly)

public class FileInputStreamDemo {
   public static void main(String args[]) {
      if (args.length != 1) {
         System.err.println("Syntax - FileInputStreamDemo file");
         return;
      }

      try {
         // Create an input stream, reading from the specified file

         InputStream fileInput = new FileInputStream(args[0]);

         // Read the first byte of data
         int data = fileInput.read();

         // Repeat : until end of file (EOF) reached
         while (data != -1) {
            // Send byte to standard output
            System.out.write(data);

            // Read next byte
            data = fileInput.read();
         }
         // Close the file
         fileInput.close();
      } catch(IOException ioe) {
         System.err.println("I/O error = " + ioe);
      }
   }
}
