import java.io.*;
import java.net.*;

public class AppServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(12345);
        System.out.println("Server started...");
        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String command;
        while (!(command = in.readLine()).equalsIgnoreCase("exit")) {
            try {
                Runtime.getRuntime().exec(command);
                out.println("Launched: " + command);
            } catch (IOException e) {
                out.println("Error launching: " + command);
            }
        }
        socket.close();
        server.close();
    }
}
