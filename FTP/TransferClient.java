import java.io.*;
import java.net.*;

public class TransferClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.print("Enter file path to send: ");
        BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader fileIn = new BufferedReader(new FileReader(user.readLine()));

        String line;
        while ((line = fileIn.readLine()) != null)
         out.println(line);

        fileIn.close();
        socket.close();
        System.out.println("File sent.");
    }
}
