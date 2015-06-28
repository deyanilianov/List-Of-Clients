package XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import client.Wrapper;

@SuppressWarnings("restriction")
public class XMLWriter implements Runnable {

	private static final String PATH = "src/main/resources/clients.xml";

	@Override
	public void run() {
		JAXBContext context;
		OutputStream output = null;
		try {
			context = JAXBContext.newInstance(Wrapper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);

			Wrapper rootElement = new Wrapper();

			output = new FileOutputStream(new File(PATH));
			marshaller.marshal(rootElement, output);
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
