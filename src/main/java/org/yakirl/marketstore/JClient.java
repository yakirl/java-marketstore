package org.yakirl.marketstore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.yakirl.marketstore.requests.*;
import org.yakirl.marketstore.responses.QueryResponse;

public class JClient {
	
	public void write(WriteRequest req) throws ProtocolException, IOException {
		Transport transport = new Transport();
		Proxy proxy = new Proxy(transport);
		proxy.send(req.getAsMap());
		Map<String, Object> res = proxy.receive();		
	}
	
	public QueryResponse query(QueryRequest req) throws Exception {
		Transport transport = new Transport();
		Proxy proxy = new Proxy(transport);
		proxy.send(req.getAsMap());
		Map<String, Object> res = proxy.receive();
		return QueryResponse.loadFromMap(res);
	}
}
