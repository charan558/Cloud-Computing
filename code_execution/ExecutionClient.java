import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ExecutionClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExecutionClient().createGUI());
    }

    private void createGUI() {
        JFrame frame = new JFrame("Execution Client");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Command execution section
        JPanel commandPanel = new JPanel();
        commandPanel.setBorder(BorderFactory.createTitledBorder("Execute Command"));
        commandPanel.setLayout(new FlowLayout());
        JLabel ipLabel = new JLabel("Server IP:");
        JTextField ipField = new JTextField(10);
        JLabel commandLabel = new JLabel("Command:");
        JTextField commandField = new JTextField(20);
        JButton commandButton = new JButton("Execute Command");
        commandPanel.add(ipLabel);
        commandPanel.add(ipField);
        commandPanel.add(commandLabel);
        commandPanel.add(commandField);
        commandPanel.add(commandButton);

        // Code execution section
        JPanel codePanel = new JPanel();
        codePanel.setBorder(BorderFactory.createTitledBorder("Execute Code"));
        codePanel.setLayout(new BorderLayout());
        JTextArea codeArea = new JTextArea(10, 50);
        JScrollPane scrollPane = new JScrollPane(codeArea);
        JPanel optionsPanel = new JPanel();
        JRadioButton javaOption = new JRadioButton("Java");
        JRadioButton cOption = new JRadioButton("C");
        ButtonGroup group = new ButtonGroup();
        group.add(javaOption);
        group.add(cOption);
        javaOption.setSelected(true);
        JButton codeButton = new JButton("Execute Code");
        optionsPanel.add(javaOption);
        optionsPanel.add(cOption);
        optionsPanel.add(codeButton);
        codePanel.add(scrollPane, BorderLayout.CENTER);
        codePanel.add(optionsPanel, BorderLayout.SOUTH);

        // Output section
        JPanel outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        JTextArea outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputPanel.add(outputScrollPane);

        // Add sections to the main panel
        panel.add(commandPanel);
        panel.add(codePanel);
        panel.add(outputPanel);

        // Action Listeners
        commandButton.addActionListener(e -> {
            String ip = ipField.getText();
            String command = commandField.getText();
            if (!ip.isEmpty() && !command.isEmpty()) {
                String result = sendToServer(ip, "command", command, null);
                outputArea.setText(result);
            } else {
                JOptionPane.showMessageDialog(frame, "IP and Command cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        codeButton.addActionListener(e -> {
            String ip = ipField.getText();
            String code = codeArea.getText();
            String codeType = javaOption.isSelected() ? "java" : "c";
            if (!ip.isEmpty() && !code.isEmpty()) {
                String result = sendToServer(ip, "code", codeType, code);
                outputArea.setText(result);
            } else {
                JOptionPane.showMessageDialog(frame, "IP and Code cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Display frame
        frame.setVisible(true);
    }

    private String sendToServer(String ip, String taskType, String param1, String param2) {
        StringBuilder response = new StringBuilder();
        try (Socket socket = new Socket(ip, 3333);
             DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
             DataInputStream din = new DataInputStream(socket.getInputStream())) {

            dout.writeUTF(taskType); // Task type
            dout.writeUTF(param1);  // Command or code type
            if (param2 != null) dout.writeUTF(param2); // Code, if any
            dout.flush();

            response.append(din.readUTF()); // Read server response
        } catch (IOException e) {
            response.append("Error: ").append(e.getMessage());
        }
        return response.toString();
    }
}