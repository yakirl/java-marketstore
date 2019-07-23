package org.yakirl.marketstore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.yakirl.marketstore.requests.*;

public class JClient {
	
	public void write(WriteRequest req) throws ProtocolException, IOException {
		Transport transport = new Transport();
		Proxy proxy = new Proxy(transport);
		proxy.send(req.getAsMap());
		Map<String, Object> res = proxy.receive();		
	}
	
	public Map<String, Object> query(QueryRequest req) throws ProtocolException, IOException {
		Transport transport = new Transport();
		Proxy proxy = new Proxy(transport);
		proxy.send(req.getAsMap());
		Map<String, Object> res = proxy.receive();
		return res;
	}
}
