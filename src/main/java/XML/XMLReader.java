package XML;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import client.Client;
import client.ClientList;

public class XMLReader {

	private List<Client> clients;
	private static final String PATH = "src/main/resources/clients.xml";

	public XMLReader() {
		clients = ClientList.getInstance().getArrayList();
	}

	public List<Client> ReadXML() {
		try {

			File fXmlFile = new File(PATH);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("clientList");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					String note = eElement.getElementsByTagName("note").item(0).getTextContent();
					String place = eElement.getElementsByTagName("place").item(0).getTextContent();
					String signingContractDate = eElement.getElementsByTagName("signingContractDate").item(0).getTextContent();
					String logoPath = eElement.getElementsByTagName("logoPath").item(0).getTextContent();

					Client client = new Client(name, place, note, signingContractDate, logoPath);

					clients.add(client);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clients;
	}
}
