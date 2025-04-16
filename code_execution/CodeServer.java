import java.io.*;
import java.net.*;

public class CodeServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8082)) {
            System.out.println("Server is listening on port 8082...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                // Receive the file
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                // Read the file name
                String fileName = dataInputStream.readUTF();
                System.out.println("Receiving file: " + fileName);

                // Save the file
                File file = new File("received_" + fileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }
                System.out.println("File received: " + file.getName());

                // Compile and execute the file
                String output = compileAndRun(file);

                // Send the output back to the client
                OutputStream outputStream = socket.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(output);
                writer.flush();

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compileAndRun(File file) {
        String fileName = file.getName();
        String language = fileName.endsWith(".java") ? "java" : fileName.endsWith(".c") ? "c" : null;

        if (language == null) {
            return "Unsupported file type.";
        }

        try {
            Process process;
            if ("java".equals(language)) {
                // Compile and run Java file
                process = new ProcessBuilder("javac", file.getName()).start();
                process.waitFor();

                process = new ProcessBuilder("java", file.getName().replace(".java", "")).start();
            } else {
                // Compile and run C file
                process = new ProcessBuilder("gcc", file.getName(), "-o", "program").start();
                process.waitFor();

                process = new ProcessBuilder("./program").start();
            }

            // Capture output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();
        } catch (Exception e) {
            return "Error during execution: " + e.getMessage();
        }
    }
}

