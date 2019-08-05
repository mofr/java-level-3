package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Server {
    static final int PORT = 4004;
    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public Server() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            UserManager userManager = new UserManager(null);  // TODO replace null with a real db connection
            System.out.println("Сервер запущен, порт " + PORT);

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this, userManager);
                clients.add(clientHandler);

                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
                new KillNotAuthorizedClient(clientHandlerThread, clientHandler, 120).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (clientSocket != null)
                    clientSocket.close();
                if (serverSocket != null)
                    serverSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsgToAllClients(String sender, String msgText) {
        clients.forEach(clientHandler -> clientHandler.sendMessage(sender, msgText));
    }

    public void sendMsgToClient(String sender, String receiverNick, String message) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getUsername().equals(receiverNick)) {
                clientHandler.sendMessage(sender, message);
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}

