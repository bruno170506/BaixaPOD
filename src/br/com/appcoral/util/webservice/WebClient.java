package br.com.appcoral.util.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class WebClient {

	private String url;

	public WebClient(String url) {
		this.url = url;
	}
	
	public String get(){
        InputStream inputStream = null;
        String result = "";
        try {
 
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
 
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }
	

	public String post(String json) {
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(json));
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse resposta = client.execute(post);
			return EntityUtils.toString(resposta.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
