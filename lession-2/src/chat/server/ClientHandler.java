package chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler implements Runnable {
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Server server;
    private String username;
    private static int clientsCount = 0;
    private boolean exit = false;
    private boolean authorized = false;
    private UserManager userManager;

    private static final Pattern QUIT_COMMAND = Pattern.compile("/quit\\s+");
    private static final Pattern NICK_COMMAND = Pattern.compile("/nick\\s+(\\w+)");
    private static final Pattern WHISPER_COMMAND = Pattern.compile("/w\\s+(\\w+)\\s+(.*)");
    private static final Pattern REGISTER_COMMAND = Pattern.compile("/register\\s+(\\w+)");
    private static final Pattern LOGIN_COMMAND = Pattern.compile("/login\\s+(\\w+)");
    private static final String NEW_CLIENTS_MSG = "Новый участник! Теперь нас = ";
    private static final String EXIT_CLIENT_MSG = "Участник вышел! Теперь нас = ";
    private Socket socket;

    public ClientHandler(Socket socket, Server server, UserManager userManager) {
        this.socket = socket;
        this.userManager = userManager;
        clientsCount++;
        username = "user_" + clientsCount;
        this.server = server;
        try {
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            server.sendMsgToAllClients("server", NEW_CLIENTS_MSG + clientsCount);

            while (!exit && inMessage.hasNext()) {
                String clientMsg = inMessage.nextLine();
                handleClientMessage(clientMsg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            exitClientSession();
        }
    }

    private void handleClientMessage(String clientMsg) {
        System.out.println(clientMsg);
        Matcher quitMatcher = QUIT_COMMAND.matcher(clientMsg);
        Matcher nickMatcher = NICK_COMMAND.matcher(clientMsg);
        Matcher whisperMatcher = WHISPER_COMMAND.matcher(clientMsg);
        Matcher registerMatcher = REGISTER_COMMAND.matcher(clientMsg);
        Matcher loginMatcher = LOGIN_COMMAND.matcher(clientMsg);
        if (quitMatcher.matches()) {
            exit = true;
        }
        else if(nickMatcher.matches()) {
            if (authorized) {
                String newUsername = nickMatcher.group(1);
                try {
                    userManager.updateUsername(username, newUsername);
                    username = newUsername;
                } catch (DuplicateUsernameException e) {
                    server.sendMsgToClient("server", username, "This nick is already used.");
                }
            }
            else {
                server.sendMsgToClient("server", username, "Please, login");
            }
        }
        else if(whisperMatcher.matches()) {
            if(authorized){
                String receiverUsername = whisperMatcher.group(1);
                String message = whisperMatcher.group(2);
                server.sendMsgToClient(username, receiverUsername, message);
            }
            else {
                server.sendMsgToClient("server", username, "Please, login");
            }
        }
        else if(registerMatcher.matches()) {
            String newUsername = registerMatcher.group(1);
            try {
                userManager.createUser(newUsername);
                authorized = true;
                username = newUsername;
            } catch (DuplicateUsernameException e) {
                server.sendMsgToClient("server", username, "This nick is already used");
            }
        }
        else if(loginMatcher.matches()) {
            String username = loginMatcher.group(1);
            if(userManager.usernameExists(username)){
                authorized = true;
            }
            else {
                server.sendMsgToClient("server", username, "Unknown username");
            }

        }
        else {
            if(authorized) {
                server.sendMsgToAllClients(username, clientMsg);
            }
            else {
                server.sendMsgToClient("server", username, "Please, login");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String sender, String msgText) {
        outMessage.println(sender + ": " + msgText);
        outMessage.flush();
    }

    public void exitClientSession() {
        server.removeClient(this);
        clientsCount--;
        server.sendMsgToAllClients("server",EXIT_CLIENT_MSG + clientsCount);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void stop() throws IOException {
        socket.close();
        exit = true;
    }
}