package domain;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class User {
    private String userID;
    private String userName;
    private String phoneNumber;

    public User() {
    }

    public User(String userID, String userName, String phoneNumber) {
        this.userID = userID;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
