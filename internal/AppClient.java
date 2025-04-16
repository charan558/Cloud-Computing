import java.io.*;
import java.net.*;

public class AppClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String appName;
        while (true) {
            System.out.print("Enter app name (exit to quit): ");
            appName = userInput.readLine();
            if (appName.equalsIgnoreCase("exit")) break;

            out.println(appName);
            System.out.println("Server: " + in.readLine());
        }
        socket.close();
    }
}
