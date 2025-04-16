import java.io.*;
import java.net.*;

public class clientop {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the Java program name to run (without .java): ");
        String programName = userInput.readLine();
        out.println(programName);

        System.out.println("Program execution request sent to the server.");

        // Receive and display the program output from the server
        System.out.println("Program Output:");
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        socket.close();
    }
}

