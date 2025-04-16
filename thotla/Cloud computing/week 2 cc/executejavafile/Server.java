// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
	//initialize socket and input stream
	private Socket		 socket = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

			String line = "";

			// reads message from client until "Over" is sent
			while (!line.equals("Over"))
			{
				try
				{
					line = in.readUTF();
					 Runtime rt = Runtime.getRuntime();
    //String[] args = new String[] {"java","abc"};
    if(!line.equals("Over"))
    {
    Process p=rt.exec("javac "+line+".java");
Process p2= rt.exec("java "+line);
BufferedReader in = new BufferedReader(  
                                new InputStreamReader(p2.getInputStream()));

             OutputStream out = p2.getOutputStream();
             String input="";
             System.out.println("Dear client, your java program output is as follows:");
             line = in.readLine();
             System.out.println(line);
             input=input+"\n";
             out.write(input.getBytes());
             //p.wait(10000);
             out.flush();
        
}
   
				}
				catch(IOException i)
				{
					System.out.println(i);
				}
				
			}
			
			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}

	public static void main(String args[])
	{
		Server server = new Server(5000);
	}
}

