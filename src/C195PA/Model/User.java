package C195PA.Model;

/**
 * Provides storage for application user's information
 * */
public class User {

    /**
     * A User's unique numeric identifier
     * */
    int userId;
    /**
     * Unique text identifier for when user's login
     * */
    String username;

    /**
     * User password to verify identity at login
     * */
    String password;

    /**
     * Constructor to instantiate a User object.
     * */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * @return the userid
     * */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId Sets the username
     * */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username Sets the username
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password Sets the password
     * */
    public void setPassword(String password) {
        this.password = password;
    }
}
