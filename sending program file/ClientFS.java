import java.io.*;
import java.net.*;

public class ClientFS {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Java file path: ");
        String filePath = userInput.readLine();
        File file = new File(filePath);

        out.println(file.getName()); // Send file name
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = fileReader.readLine()) != null) 
        out.println(line);
        
        
        out.println("EOF"); // Indicate end of file
        fileReader.close();

        System.out.println("Output from server:");
        while ((line = in.readLine()) != null)
         System.out.println(line);

        socket.close();
    }
}
