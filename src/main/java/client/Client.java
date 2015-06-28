package client;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@SuppressWarnings("restriction")
@XmlRootElement
public class Client {

	private String name;
	private String place;
	private String note;
	private String signingContractDate;

	private File file;
	private String logoPath;
	private boolean isClienHaveLogo;

	public Client() {
	}

	public Client(String name, String place, String note, String signingContractDate, String logoPath) {
		this.name = name;
		this.place = place;
		this.note = note;
		this.signingContractDate = signingContractDate;
		this.logoPath = logoPath;
	}

	@Override
	public String toString() {
		return this.name + " " + this.note + " " + this.place + " " + this.signingContractDate + " ";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSigningContractDate() {
		return signingContractDate;
	}

	public void setSigningContractDate(String signingContractDate) {
		this.signingContractDate = signingContractDate;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public boolean isClienHaveLogo() {
		return isClienHaveLogo;
	}

	@XmlTransient
	public void setClienHaveLogo(boolean isClienHaveLogo) {
		this.isClienHaveLogo = isClienHaveLogo;
	}

}
