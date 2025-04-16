import java.io.*;
import java.net.*;

public class TransferServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("Server started...");
        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter fileOut = new PrintWriter(new FileWriter("received.txt"));

        String line;
        while ((line = in.readLine()) != null) 
        fileOut.println(line);

        fileOut.close();
        socket.close();
        server.close();
        System.out.println("File received.");
    }
}
