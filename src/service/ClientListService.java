package service;

import domain.Client;
import view.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:37 PM
 */
public class ClientListService {
    private List<Client> clientList = new ArrayList<>();
    private String srcPath = "ClientData.txt";

    public ClientListService() {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 4) {
            String clientID = elements.get(i + 0);
            String clientName = elements.get(i + 1);
            String password = elements.get(i + 2);
            String phoneNumber = elements.get(i + 3);

            clientList.add(new Client(clientID, clientName, password, phoneNumber));
        }
    }

    public void showClientList() {
        System.out.println();
        System.out.println(String.format("%52s", " ").replace(' ', '-'));

        String str = String.format("|%-12s|%-20s|%-16s|", "Client ID", "Client Name", "Phone Number");
        System.out.println(str);

        System.out.println(String.format("%52s", " ").replace(' ', '-'));

        for (Client client : clientList) {
            System.out.println(client);
        }

        System.out.println(String.format("%52s", " ").replace(' ', '-'));
        System.out.println();
    }

    public void createClientProfile() {
        System.out.print("\nPlease enter your information.[Customer Only]\n");// login and register
        // not allow repeat same ID, no need input from user
        // get last order ID
        String lastId = clientList.get(clientList.size() - 1).getUserID();
        // get the number part of the ID and plus 1
        int newId = Integer.parseInt(lastId.substring(1)) + 1;
        // form a new ID
        String clientID = "C" + String.format("%04d", newId);
        System.out.println("Client ID: " + clientID);

        System.out.print("Client Name: ");
        String name = Utility.readString(20);

        System.out.print("Password: ");
        String password = Utility.readString(20);

        System.out.print("Phone number: ");
        String phoneNumber = Utility.readString(13);

        clientList.add(new Client(clientID, name, password, phoneNumber));
        saveFile();
        System.out.println("Added Successfully.");
    }

    public Client getClientById(String clientID) {
        clientID = clientID.toUpperCase();

        for (Client client : clientList) {
            if (clientID.equals(client.getUserID())) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getAllClients() {
        return clientList;
    }

    private void saveFile() {
        String str = "";

        for (Client client : clientList) {
            str += String.format("%s,%s,%s,%s\n", client.getUserID(), client.getUserName(), client.getPassword(), client.getPhoneNumber());
        }

        Utility.saveFile(srcPath, str);
    }
}
