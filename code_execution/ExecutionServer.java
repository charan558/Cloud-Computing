import java.io.*;
import java.net.*;

public class ExecutionServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3333)) {
            System.out.println("Server is running and waiting for client connections...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream din = new DataInputStream(socket.getInputStream());
                     DataOutputStream dout = new DataOutputStream(socket.getOutputStream())) {

                    String taskType = din.readUTF(); // Task type: "command" or "code"
                    String response;

                    if ("command".equals(taskType)) {
                        String command = din.readUTF(); // Command to execute
                        response = executeCommand(command);
                    } else if ("code".equals(taskType)) {
                        String codeType = din.readUTF(); // Code type: "java" or "c"
                        String code = din.readUTF();    // Actual code
                        response = executeCode(codeType, code);
                    } else {
                        response = "Unknown task type.";
                    }

                    dout.writeUTF(response);
                    dout.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Method to execute a system command
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("Error: Command execution failed with exit code ").append(exitCode);
            }
        } catch (IOException | InterruptedException e) {
            output.append("Error executing command: ").append(e.getMessage());
        }
        return output.toString();
    }

    // Method to execute Java or C code
    private static String executeCode(String codeType, String code) {
        String output;
        try {
            // Save code to a temporary file
            String fileName = codeType.equals("java") ? "hello.java" : "hello.c";
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(code);
            }

            if (codeType.equals("java")) {
                // Compile and execute Java code
                Process compile = Runtime.getRuntime().exec("javac " + fileName);
                compile.waitFor();
                Process execute = Runtime.getRuntime().exec("java hello");
                output = getProcessOutput(execute);
            } else if (codeType.equals("c")) {
                // Compile and execute C code
                Process compile = Runtime.getRuntime().exec("gcc " + fileName + " -o hello");
                compile.waitFor();
                Process execute = Runtime.getRuntime().exec("./hello");
                output = getProcessOutput(execute);
            } else {
                output = "Unsupported code type.";
            }
        } catch (IOException | InterruptedException e) {
            output = "Error executing code: " + e.getMessage();
        }
        return output;
    }

    private static String getProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return output.toString();
    }
}
