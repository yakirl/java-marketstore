package org.yakirl.marketstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Transport {
	private URL url;
	HttpURLConnection connection = null;
	private static final String defaultURL = "http://localhost:5993/rpc";
		
	public Transport(String address) throws MalformedURLException {
		url  = new URL(address);
	}
	
	public Transport() throws MalformedURLException {
		this(defaultURL);
	}
	
	public OutputStream getOutSteam() throws ProtocolException, IOException {
		connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "application/x-msgpack");		    
	    connection.setRequestProperty("Content-Language", "en-US");
	    connection.setRequestProperty("Accept-Encoding", "gzip, deflate"); // check
	    connection.setRequestProperty("Accept", "*/*");
	    connection.setUseCaches(false);
	    connection.setDoOutput(true);
	    return connection.getOutputStream();
	}

	public InputStream getInStream() throws ProtocolException, IOException {
		if (connection == null) {
			throw new IOException("no connection");
		}
		return connection.getInputStream();
	}

}
