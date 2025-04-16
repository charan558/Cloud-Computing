import java.io.*;
import java.net.*;

public class servercp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String programName = in.readLine();
            System.out.println("Running program: " + programName);

            try {
                Process process = Runtime.getRuntime().exec("java " + programName);
                BufferedReader programOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader programError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                // Send program output to the client
                String line;
                while ((line = programOutput.readLine()) != null) {
                    out.println(line);
                }

                // Send program errors to the client (if any)
                while ((line = programError.readLine()) != null) {
                    out.println(line);
                }

                out.println("Execution completed.");
                programOutput.close();
                programError.close();
            } catch (IOException e) {
                out.println("Error: Unable to execute the program. " + e.getMessage());
            }

            socket.close();
        }
    }
}

