package chat.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private Connection connection;

    @BeforeEach
    void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:");
        Statement statement = connection.createStatement();
        statement.executeUpdate("create table users (id string primary key, name string not null, password string not null)");
    }

    @AfterEach
    void closeDatabaseConnection() throws SQLException {
        connection.close();
    }

    @Test
    void usernameExists() {
        UserManager userManager = new UserManager(connection);

        // Act
        boolean exists = userManager.usernameExists("test_user");

        // Assert
        assertFalse(exists);
    }

    @Test
    void createUser() throws DuplicateUsernameException {
        UserManager userManager = new UserManager(connection);

        // Act
        userManager.createUser("test_user");

        // Assert
        assertTrue(userManager.usernameExists("test_user"));
    }

    @Test
    void createUserWithExistingUsername() throws DuplicateUsernameException {
        UserManager userManager = new UserManager(connection);
        userManager.createUser("test_user");

        // Act
        assertThrows(DuplicateUsernameException.class, () ->
                userManager.createUser("test_user")
        );

        // Assert
        assertTrue(userManager.usernameExists("test_user"));
    }

    @Test
    void updateUser() throws DuplicateUsernameException {
        UserManager userManager = new UserManager(connection);
        userManager.createUser("test_user");

        // Act
        userManager.updateUsername("test_user", "test_user2");

        // Assert
        assertFalse(userManager.usernameExists("test_user"));
        assertTrue(userManager.usernameExists("test_user2"));
    }

    @Test
    void updateUserWithTheSameUsername() throws DuplicateUsernameException {
        UserManager userManager = new UserManager(connection);
        userManager.createUser("test_user");

        // Act
        userManager.updateUsername("test_user", "test_user");

        // Assert
        assertTrue(userManager.usernameExists("test_user"));
    }

    @Test
    void updateUserWithExistingUsername() throws DuplicateUsernameException {
        UserManager userManager = new UserManager(connection);
        userManager.createUser("user_1");
        userManager.createUser("user_2");

        // Act
        assertThrows(DuplicateUsernameException.class, () ->
                userManager.updateUsername("user_1", "user_2")
        );

        // Assert
        assertTrue(userManager.usernameExists("user_1"));
        assertTrue(userManager.usernameExists("user_2"));
    }
}