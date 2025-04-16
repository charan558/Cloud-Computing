import java.net.*;  
import java.io.*;  

class MyServer1 {  
    public static void main(String args[]) {  
        ServerSocket ss = null;  
        Socket s = null;  
        DataInputStream din = null;  
        DataOutputStream dout = null;  

        try {  
            ss = new ServerSocket(3333);  
            System.out.println("Server waiting for client connection...");  
            s = ss.accept();  
            System.out.println("Client connected!");  

            din = new DataInputStream(s.getInputStream());  
            dout = new DataOutputStream(s.getOutputStream());  

            String command = "";  
            while (!command.equals("stop")) {  
                // Receive the command from the client
                command = din.readUTF();  
                System.out.println("Client wants to execute: " + command);  

                // Execute the command on the server side
                String result = executeCommand(command);

                // Send the command output back to the client
                dout.writeUTF(result);  
                dout.flush();  
            }
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
        } finally {  
            try {  
                if (din != null) din.close();  
                if (dout != null) dout.close();  
                if (s != null) s.close();  
                if (ss != null) ss.close();  
            } catch (IOException e) {  
                System.out.println("Error closing resources: " + e.getMessage());  
            }  
        }  
    }  

    // Method to execute a system command and return the output
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            // Create a process to execute the command
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            // Read the output of the command and append it to the StringBuilder
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            int exitCode = process.waitFor();  // Wait for the command to complete
            if (exitCode != 0) {
                output.append("Error: Command execution failed with exit code ").append(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            output.append("Error executing command: ").append(e.getMessage());
        }
        return output.toString();
    }
}

