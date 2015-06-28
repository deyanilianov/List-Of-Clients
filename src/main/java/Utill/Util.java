package Utill;

import java.util.List;

import client.Client;

public class Util {
	
	public static boolean isValideExtention(String path) {
		int lastDotPosition = path.lastIndexOf('.');
		if (lastDotPosition != -1) {
			String extension = path.substring(lastDotPosition + 1);
			if (extension.equals("jpg") || extension.equals("png") || extension.equals("bmp")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public static boolean isNameValid(String name, List<Client> clients){
		for (Client client : clients) {
			if(client.getName().toLowerCase().equals(name.toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
}
