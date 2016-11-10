package br.com.baixapod.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {

	private String url;

	public WebClient(String url) {
		this.url = url;
	}
	
	public String post() {
		return post("");
	}
	
	public String post(String json) {
		String resposta = "";
		try {
			URL urlPost = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlPost.openConnection();
			connection.setDoOutput(true);
			PrintStream output = new PrintStream(connection.getOutputStream());
			output.println(json);
			connection.connect();
			resposta = getStringFromInputStream(connection.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  resposta;
	}
	
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	
	
	
}

