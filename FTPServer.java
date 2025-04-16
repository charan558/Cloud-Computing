import java.io.*;
import java.net.*;

public class FTPServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8082)) {
            System.out.println("Server is listening on port 8080...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Receive the file
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Read the file name
            String fileName = dataInputStream.readUTF();
            System.out.println("Receiving file: " + fileName);

            // Create a file output stream to save the file
            FileOutputStream fileOutputStream = new FileOutputStream("received_" + fileName);
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read file data from the client and write it to the file
            while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File received and saved as: received_" + fileName);

            // Close resources
            fileOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
