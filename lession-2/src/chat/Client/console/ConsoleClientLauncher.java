package chat.Client.console;

import java.io.IOException;

public class ConsoleClientLauncher {
    public static void main(String[] args) {
        try {
            new ConsoleClient();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
