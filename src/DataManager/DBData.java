package DataManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DBData {
	private String url;
	private String usr;
	private String pwd;
	File archivo = new File("datosbd.txt");

	public void init() {
		
		try {
			FileReader leerArchivo = new FileReader(archivo);
			BufferedReader br = new BufferedReader(leerArchivo);
			String linea1 = br.readLine();
			String[] aux1 = linea1.split(": ");
			System.out.println(aux1);
			setUrl(aux1[1]);

			String linea2 = br.readLine();
			String[] aux2 = linea2.split(": ");
			setUsr(aux2[1]);

			String linea3 = br.readLine();
			String[] aux3 = linea3.split(": ");
			setPwd(aux3[1]);

			br.close();
			leerArchivo.close();
		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
