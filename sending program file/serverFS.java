import java.io.*;
import java.net.*;

public class serverFS {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server running...");

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String fileName = in.readLine(); // Receive file name
            PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName));
            String line;
            while (!(line = in.readLine()).equals("EOF")) fileWriter.println(line);
            fileWriter.close();

            Process compile = Runtime.getRuntime().exec("javac " + fileName);
            compile.waitFor();

            
                Process run = Runtime.getRuntime().exec("java " + fileName.replace(".java", ""));
                BufferedReader output = new BufferedReader(new InputStreamReader(run.getInputStream()));
                while ((line = output.readLine()) != null) out.println(line);
                output.close();
            
            socket.close();
        }
    }
}
