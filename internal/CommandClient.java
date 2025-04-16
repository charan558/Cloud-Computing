import java.io.*;
import java.net.*;

public class CommandClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the command to execute on the server: ");
        String command = userInput.readLine();
        out.println(command);

        String line;
        while (!(line = in.readLine()).equals("END")) {
            System.out.println(line);
        }

        socket.close();
    }
}
