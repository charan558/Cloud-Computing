import java.net.*;  
import java.io.*;  

class MyClient1 {  
    public static void main(String args[]) throws Exception {  
        Socket s = new Socket("localhost", 3333);  
        DataInputStream din = new DataInputStream(s.getInputStream());  
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  

        String str = "", str2 = "";  
        while (!str.equals("stop")) {  
            System.out.print("Enter command to execute on server: ");
            str = br.readLine();  // Get command or filename from the user
            dout.writeUTF(str);   // Send the command to the server
            dout.flush();  
            
            // Get server response (output of the command execution)
            str2 = din.readUTF();  
            System.out.println("Server output: " + str2);  
        }

        dout.close();  
        s.close();  
    }  
}

