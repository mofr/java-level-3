package ru.dasha.chat.client;

import java.io.*;
import java.net.Socket;

public class Messenger {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Thread readerThread;
    private Listener listener;

    public interface Listener {
        void onMessage(String serverMessage);
        void onIOException(IOException e);
        void onEndOfSession();
    }

    public Messenger(Socket socket, Listener listener) throws IOException {
        this.listener = listener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
        readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String incomingMessage = in.readLine();
                        if (incomingMessage == null) {
                            listener.onEndOfSession();
                            break;
                        }
                        else {
                            listener.onMessage(incomingMessage);
                        }
                    }
                } catch (IOException e) {
                    listener.onIOException(e);
                }
            }
        });
    }

    public void start() {
        readerThread.start();
    }

    public void stop() {
        readerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }
}

