package chat.Client.gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame {
    private final JButton sendButton;
    private final JTextField messageField;
    private final JTextPane logPane;
    private final Document logDocument;
    private Listener listener;

    public interface Listener {
        void sendChatMessage(String message);
    }

    public ChatWindow() {
        setTitle("Chat");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sendButton = new JButton("send");
        messageField = new JTextField();
        logPane = new JTextPane();
        logPane.setPreferredSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(logPane);
        logPane.setEditable(false);
        JScrollPane scroll = new JScrollPane(logPane);
        add(scroll);
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
        messagePanel.add(messageField);
        messagePanel.add(sendButton);
        add(messagePanel);
        logDocument = logPane.getDocument();
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        setVisible(true);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void displayIncomingMessage(String message) {
        displayMessage(message);
    }

    private void sendMessage() {
        String message = messageField.getText();
        messageField.setText("");
        listener.sendChatMessage(message);
    }

    private void displayMessage(String message) {
        try {
            logDocument.insertString(logDocument.getLength(), message + "\n", null);}
        catch (BadLocationException x) {
            System.out.println(x.getMessage());
        }
    }
}
