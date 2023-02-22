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
            String phoneNumber = elements.get(i + 2);

            clientList.add(new Client(clientID, clientName, phoneNumber));
        }
    }

    public void showClientList() {
        System.out.println();
        System.out.println(String.format("%77s", " ").replace(' ', '-'));

        String str = String.format("|%-12s|%-30s|%-16s|", "Client ID", "Client Name", "Phone Number");
        System.out.println(str);

        System.out.println(String.format("%77s", " ").replace(' ', '-'));

        for (Client client : clientList) {
            System.out.println(client);
        }

        System.out.println(String.format("%77s", " ").replace(' ', '-'));
        System.out.println();
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
}
