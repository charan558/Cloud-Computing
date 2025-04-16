import java.net.*;
import java.io.*;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private FileOutputStream fileOut = null;

    public Server(int port) {
        try {
            // Start the server and wait for a connection
            server = new ServerSocket(port);
            System.out.println("Server started and waiting for a client...");

            socket = server.accept();
            System.out.println("Client connected");

            // Input stream to receive data from the client
            in = new DataInputStream(socket.getInputStream());

            while (true) {
                // Read the filename from the client
                String filename = in.readUTF();

                // Check if client wants to terminate connection
                if (filename.equalsIgnoreCase("exit")) {
                    System.out.println("Client has terminated the connection.");
                    break;
                }

                System.out.println("Receiving file: " + filename);

                // Prepare to write to a file with the received filename
                fileOut = new FileOutputStream("received_" + filename);
                int bytesRead;
                byte[] buffer = new byte[4096];

                // Receive file data and write to the file
                while ((bytesRead = in.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);

                    // Check if there's no more data to read
                    if (in.available() == 0) break;
                }

                System.out.println("File " + filename + " received successfully.");

                // Close file output stream
                fileOut.close();
            }

            // Close input stream and socket
            in.close();
            socket.close();
            server.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server(5000);
    }
}

