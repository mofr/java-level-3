package chat.Client.console;

import ru.dasha.chat.client.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConsoleClient {
    public ConsoleClient() throws IOException {
        Socket socket = new Socket("localhost", 4004);
        Messenger.Listener listener = new Messenger.Listener() {
            @Override
            public void onMessage(String incomingMessage) {
                System.out.println(incomingMessage);
            }

            @Override
            public void onIOException(IOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }

            @Override
            public void onEndOfSession() {
                System.out.println("end of session");
                System.exit(0);
            }
        };
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Messenger messenger = new Messenger(socket, listener);
            messenger.start();
            while (true) {
                String message = reader.readLine();
                messenger.send(message);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
