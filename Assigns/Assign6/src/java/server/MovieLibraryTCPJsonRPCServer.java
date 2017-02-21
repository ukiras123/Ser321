package server;

public class MovieLibraryTCPJsonRPCServer extends Thread {
	private Socket sock;
	private int id;
	private MovieLibrarySkeleton skeleton;
	
	public MovieLibraryTCPJsonRPCServer(Socket sock, int id, MovieLibrary movieLibrary) {
		this.sock = sock;
		this.id = id;
		skeleton = new MovieLibrarySkeleton(movieLibrary);
	}
	
	public void run() {
		try {
			OutputStream outSock = sock.getOutputStream();
			InputStream inSock = sock.getInputStream();
			byte clientInput[] = new byte[1024]; // up to 1024 bytes in a message
			int numr = inSock.read(clientInput, 0, 1024);
			
			if (numr != -1) {
				String request = new String(clientInput, 0, numr);
				Sytem.out.println("request is: " + request);
				String response = skeleton.callMethod(request);
				byte clientOut[] = response.getBytes();
				outSock.write(clientOut, 0, clientOut.length);
				
				System.out.println("response is: " + response);
			}
			
			inSock.close();
			outSock.close();
			sock.close();
		} catch(IOException e) {
			System.out.println("I/O exception occurred for the connection:\n" + e.getMessage());
		}
	}
}
