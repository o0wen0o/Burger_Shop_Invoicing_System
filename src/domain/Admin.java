package domain;

/**
 * @author o0wen0o
 * @create 2023-02-22 8:24 PM
 */
public class Admin extends User {
    public Admin() {
    }

    public Admin(String userID, String userName, String phoneNumber) {
        super(userID, userName, phoneNumber);
    }

    @Override
    public String toString() {
        String str = String.format("|%-12s|%-30s|%-16s|", getUserID(), getUserName(), getPhoneNumber());
        return str;
    }
}
