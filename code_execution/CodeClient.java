import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CodeClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8082)) {
            System.out.println("Connected to the server.");

            Scanner scanner = new Scanner(System.in);

            int choice = 0;
            while (true) {
                try {
                    // Prompt user to enter a choice
                    System.out.println("Enter '1' to write code or '2' to upload a file:");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    if (choice == 1 || choice == 2) {
                        break; // Valid choice, exit loop
                    } else {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            File file;

            if (choice == 1) {
                // User writes code
                System.out.println("Enter the programming language (java/c): ");
                String language = scanner.nextLine();
                System.out.println("Enter your code (end with 'EOF' on a new line):");

                // Read code from user input
                StringBuilder code = new StringBuilder();
                String line;
                while (!(line = scanner.nextLine()).equals("EOF")) {
                    code.append(line).append("\n");
                }

                // Save the code to a file
                file = new File("user_code." + language);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(code.toString());
                }
            } else {
                // User uploads a file
                System.out.println("Enter the path to the file:");
                String filePath = scanner.nextLine();
                file = new File(filePath);

                if (!file.exists()) {
                    System.out.println("File does not exist at: " + file.getAbsolutePath());
                    return;
                }
            }

            // Send the file to the server
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(file.getName());

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("File sent successfully.");

            // Receive output from the server
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Output from the server:");
            String serverOutput;
            while ((serverOutput = reader.readLine()) != null) {
                System.out.println(serverOutput);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

