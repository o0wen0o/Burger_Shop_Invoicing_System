package domain;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class User {
    private String userID;
    private String userName;
    private String password;
    private String phoneNumber;

    public User() {
    }

    public User(String userID, String userName, String password, String phoneNumber) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
