package org.yakirl.marketstore.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	
	Map<String, Object> map  = new HashMap<String, Object>();
	String operation;
	
	public Request(String operation) {
		this.operation = operation;
	}
	
	public Map<String, List<Map<String, Object>>> getParams() {
		return null;
	}
	
	public Map<String, Object> getMapping() {
		map.put("id", "1");
		map.put("jsonrpc", "2.0");
		map.put("method", String.format("DataService.%s", operation));	
		map.put("params", getParams());
		System.out.println(map);
		return map;
	}

}
