package client;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper {

	protected List<Client> clientList;

	public Wrapper() {
		this.clientList = ClientList.getInstance().getArrayList();
	}

	public List<Client> getClientList() {
		return this.clientList;
	}

}
