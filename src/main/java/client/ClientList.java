package client;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
public class ClientList {

    private static ArrayList<Client> clientList;

    private static ClientList instance = null;

    private ClientList() {
        clientList = new ArrayList<Client>();
    }

    public static ClientList getInstance() {
        if (instance == null) {
            instance = new ClientList();
        }
        return instance;
    }

    public ArrayList<Client> getArrayList() {
        return clientList;
    }
}
