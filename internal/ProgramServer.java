import java.io.*;
import java.net.*;

public class ProgramServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            
            String programName = in.readLine();
            System.out.println("Running program: " + programName);

            Process process = Runtime.getRuntime().exec("java " + programName);
            BufferedReader programOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            System.out.println("Program Output:");
            String line;
            while ((line = programOutput.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("Execution completed.");
            programOutput.close();
            socket.close();
        }
    }
}
