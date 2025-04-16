import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
             
            System.out.println("Connected. Type 'exit' to stop communication.");
            for (String msg; !(msg = input.readLine()).equalsIgnoreCase("exit"); )
                output.println(msg);
  
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

