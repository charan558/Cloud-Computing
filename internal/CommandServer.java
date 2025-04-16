import java.io.*;
import java.net.*;

public class CommandServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String command = in.readLine();
            System.out.println("Running command: " + command);

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = processOutput.readLine()) != null) {
                out.println(line);
            }

            out.println("END");
            processOutput.close();
            socket.close();
        }
    }
}
