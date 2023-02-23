package domain;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class Client extends User {

    public Client() {
    }

    public Client(String userID, String userName, String password, String phoneNumber) {
        super(userID, userName, password, phoneNumber);
    }

    @Override
    public String toString() {
        String str = String.format("|%-12s|%-20s|%-16s|", getUserID(), getUserName(), getPhoneNumber());
        return str;
    }
}
