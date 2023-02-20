package domain;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class Client extends User {
    private PaymentStatus paymentStatus = PaymentStatus.NO_ORDER;

    public Client() {
    }

    public Client(String userID, String userName, String phoneNumber, PaymentStatus paymentStatus) {
        super(userID, userName, phoneNumber);
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        String str = String.format("|%-12s|%-30s|%-16s|%-14s|", getUserID(), getUserName(), getPhoneNumber(), getPaymentStatus());
        return str;
    }
}
