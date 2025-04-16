import java.io.*;
import java.net.*;

public class Client1 {
    
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private DataInputStream serverInput = null;

    
    public Client1(String address, int port) {
        try {
    
            socket = new Socket(address, port);
            System.out.println("Connected to server at " + address + ":" + port);
            
    
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            serverInput = new DataInputStream(socket.getInputStream());

        } catch (UnknownHostException u) {
            System.out.println("Host not found: " + u.getMessage());
            return;
        } catch (IOException i) {
            System.out.println("IOException: " + i.getMessage());
            return;
        }

        
        try {
            String fileName = "";

            
            while (true) {
                System.out.print("Enter Java file name (without extension) to execute or 'Over' to quit: ");
                fileName = input.readLine();
                
              
                out.writeUTF(fileName);

    
                if (fileName.equalsIgnoreCase("Over")) {
                    break;
                }

            
                String output = serverInput.readUTF();
                System.out.println("Output from server:\n" + output);
            }
        } catch (IOException i) {
            System.out.println("Error during communication: " + i.getMessage());
        } finally {
            
            closeConnection();
        }
    }

   
    private void closeConnection() {
        try {
            if (input != null) input.close();
            if (out != null) out.close();
            if (serverInput != null) serverInput.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException i) {
            System.out.println("Error closing resources: " + i.getMessage());
        }
    }

    public static void main(String[] args) {
        
        new Client1("localhost", 5000);
    }
}

