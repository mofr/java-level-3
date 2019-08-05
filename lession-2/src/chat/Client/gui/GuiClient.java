package chat.Client.gui;

import ru.dasha.chat.client.Messenger;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class GuiClient {
    public GuiClient() throws IOException {
        ChatWindow chatWindow = new ChatWindow();
        Socket socket = new Socket("localhost", 4004);
        Messenger.Listener listener = new Messenger.Listener() {
            @Override
            public void onMessage(String incomingMessage) {
                chatWindow.displayIncomingMessage(incomingMessage);
            }

            @Override
            public void onIOException(IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }

            @Override
            public void onEndOfSession() {
                System.exit(0);
            }
        };

        Messenger messenger = new Messenger(socket, listener);
        ChatWindow.Listener chatWindowListener = new ChatWindow.Listener() {
            @Override
            public void sendChatMessage(String message) {
                try {
                    messenger.send(message);
                } catch (IOException e) {
                    chatWindow.displayIncomingMessage("Не получилось отправить сообщение: " + e.getMessage());
                }
            }
        };
        chatWindow.setListener(chatWindowListener);
        chatWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                messenger.stop();
            }
        });
        messenger.start();
    }
}
