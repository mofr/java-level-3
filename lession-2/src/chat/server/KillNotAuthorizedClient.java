package chat.server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class KillNotAuthorizedClient extends Thread{
    private final Thread clientHandlerThread;
    private final ClientHandler clientHandler;
    private final int timeout;

    public KillNotAuthorizedClient(Thread clientHandlerThread, ClientHandler clientHandler, int timeout) {
        this.clientHandlerThread = clientHandlerThread;
        this.clientHandler = clientHandler;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(timeout);
            if (!clientHandler.isAuthorized()) {
                clientHandler.stop();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
