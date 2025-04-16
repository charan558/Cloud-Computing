import java.io.*;
import java.net.*;

public class Server{
    public static void main(String[] args) {
    	System.out.println("Waiting for client.....");
        try (ServerSocket serverSocket = new ServerSocket(12345);
             Socket socket = serverSocket.accept();
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
             
            System.out.println("Connected to sender: " + socket.getInetAddress());
            for (String message; (message = input.readLine()) != null && !message.equalsIgnoreCase("exit"); )
                System.out.println("Received: " + message);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

