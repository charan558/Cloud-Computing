import java.io.*;
import java.net.*;

public class FileSender {
    public static void main(String[] args) {
        try {
            // Connect to the server running on localhost at port 5000
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to the server!");

            // File to send
            File file = new File("mani.txt"); // Replace "example.txt" with your file name
            if (!file.exists()) {
                System.out.println("File does not exist.");
                return;
            }

            // Output stream to send the file
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            // Send the file name
            dataOutputStream.writeUTF(file.getName());

            // Read the file and send its contents
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent: " + file.getName());

            // Close resources
            fileInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

