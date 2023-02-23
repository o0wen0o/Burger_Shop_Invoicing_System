package domain;

/**
 * @author o0wen0o
 * @create 2023-02-22 8:24 PM
 */
public class Admin extends User {
    public Admin() {
    }

    public Admin(String userID, String userName, String password, String phoneNumber) {
        super(userID, userName, password, phoneNumber);
    }

    @Override
    public String toString() {
        String str = String.format("|%-12s|%-20s|%-16s|", getUserID(), getUserName(), getPhoneNumber());
        return str;
    }
}
