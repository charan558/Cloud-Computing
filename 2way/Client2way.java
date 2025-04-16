import java.io.*;
import java.net.*;

public class Client2way {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server!");

            for (String clientMessage; !(clientMessage = console.readLine()).equalsIgnoreCase("exit"); ) {
                output.println(clientMessage);
                System.out.println("Server: " + input.readLine());
            }

            System.out.println("Disconnected from server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

