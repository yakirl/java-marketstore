package org.yakirl.marketstore;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.Map;

import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

public class Proxy {
	
	Transport transport;
	
	public Proxy(Transport transport) {
		this.transport = transport;
	}
	
	public void send(Map<String, Object> map) throws ProtocolException, IOException {
	    JSONObject root = new JSONObject(map);		    
	    ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
	    byte[] bytes = objectMapper.writeValueAsBytes(root);		    
	    DataOutputStream wr = new DataOutputStream (
	        transport.getOutSteam());
	    wr.write(bytes);
	    wr.close();
	}
	
	public Map<String, Object> receive() throws ProtocolException, IOException {  		    		  
	    InputStream is = transport.getInStream();
	    ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());
	    Map<String, Object> jsonMap = mapper.readValue(is, Map.class);
	    return jsonMap;
	}
}
