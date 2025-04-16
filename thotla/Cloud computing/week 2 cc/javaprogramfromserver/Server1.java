import java.io.*;
import java.net.*;

public class Server1 {
    
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    
    public Server1(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            
            while (true) {
                System.out.println("Waiting for a client...");
                socket = server.accept();
                System.out.println("Client connected.");

    
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    
    private static class ClientHandler extends Thread {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
    
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

             
                String fileName = in.readUTF();
                if (fileName.equalsIgnoreCase("Over")) {
                    return;
                }

                
                String output = executeJavaProgram(fileName);

                
                out.writeUTF(output);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                    System.out.println("Connection with client closed.");
                } catch (IOException e) {
                    System.out.println("Error closing resources: " + e.getMessage());
                }
            }
        }

        
        private String executeJavaProgram(String fileName) {
            String output = "";

            try {
                
                Process compileProcess = Runtime.getRuntime().exec("javac " + fileName + ".java");
                int compileExitCode = compileProcess.waitFor();

                if (compileExitCode != 0) {
                    return "Compilation failed.";
                }

                
                Process executeProcess = Runtime.getRuntime().exec("java " + fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(executeProcess.getInputStream()));
                String line;

                
                while ((line = reader.readLine()) != null) {
                    output += line + "\n";
                }

                
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(executeProcess.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    output += "Error: " + line + "\n";
                }

            } catch (IOException | InterruptedException e) {
                output = "Error executing the Java program: " + e.getMessage();
            }

            return output;
        }
    }

    public static void main(String[] args) {
        new Server1(5000);
    }
}

