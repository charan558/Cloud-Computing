import java.io.*;
import java.net.*;

public class ProgramClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the Java program name to run (without .java): ");
        String programName = userInput.readLine();
        out.println(programName);

        System.out.println("Program execution request sent to the server.");
        socket.close();
    }
}
