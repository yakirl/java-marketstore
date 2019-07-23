package org.yakirl.marketstore;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.List;
import java.util.Map;

import org.yakirl.marketstore.requests.*;
import org.yakirl.marketstore.responses.QueryResponse;

public class JClient {
	
	public void write(WriteRequest req) throws ProtocolException, IOException {
		exec(req);		
	}
	
	public QueryResponse query(QueryRequest req) throws Exception {
		return QueryResponse.loadFromMap(exec(req));
	}
	
	public List<String> listSymbols() throws Exception {			
		Map<String, Object> res = exec((new Request("ListSymbols")));
		Map<String, Object> mainResult = (Map<String, Object>)res.get("result");
		return (List<String>)mainResult.get("Results");
	}
	
	private Map<String, Object> exec(Request req) throws ProtocolException, IOException {
		Transport transport = new Transport();
		Proxy proxy = new Proxy(transport);
		proxy.send(req.getMapping());
		Map<String, Object> res = proxy.receive();
		return res;
	}
}
