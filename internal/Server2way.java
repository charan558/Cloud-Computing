import java.io.*;
import java.net.*;

public class Server2way {
    public static void main(String[] args) {
    		System.out.println("waiting for client");
        try (ServerSocket serverSocket = new ServerSocket(5000);
             Socket socket = serverSocket.accept();
             BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Client connected!");

            for (String clientMessage; (clientMessage = inputFromClient.readLine()) != null && !clientMessage.equalsIgnoreCase("exit"); ) {
                System.out.println("Client: " + clientMessage);
                System.out.print("You: ");
                outputToClient.println(serverInput.readLine());
            }

            System.out.println("Client disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

