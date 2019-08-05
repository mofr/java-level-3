package chat.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserManager {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;

    public UserManager(Connection connection) {


    }

    public void createUser(String username) throws DuplicateUsernameException {

    }

    public void updateUsername(String username, String newUsername) throws DuplicateUsernameException {

    }

    public boolean usernameExists(String username) {
        return true;
    }


}
