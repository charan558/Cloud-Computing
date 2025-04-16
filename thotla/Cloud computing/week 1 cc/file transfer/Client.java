import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket socket = null;
    private FileInputStream fileIn = null;
    private DataOutputStream out = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the server");

            // Open output stream
            out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Prompt user for filename
                System.out.print("Enter the filename to send (or 'exit' to quit): ");
                String filename = scanner.nextLine();

                // Check if user wants to exit
                if (filename.equalsIgnoreCase("exit")) {
                    out.writeUTF("exit");
                    break;
                }

                // Send the filename to the server
                out.writeUTF(filename);

                try {
                    // Open the specified file
                    fileIn = new FileInputStream(filename);
                    int bytesRead;
                    byte[] buffer = new byte[4096];

                    // Send file data to server
                    while ((bytesRead = fileIn.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    System.out.println("File " + filename + " sent successfully.");

                    fileIn.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + e.getMessage());
                }
            }

            // Close scanner, output stream, and socket
            scanner.close();
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("192.168.124.38", 5000);
    }
}

