package org.yakirl.marketstore.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestroyRequest extends Request {
	
	private String tbk;
	
	public DestroyRequest(String tbk) {
		super("Destroy");
		this.tbk = tbk;
	}
	
	public Map<String, List<Map<String, Object>>> getParams() {
		
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("key", tbk);

		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();
		requests.add(req);
		
		Map<String, List<Map<String, Object>>> params = new HashMap<String, List<Map<String, Object>>>();
		params.put("requests", requests);
		
		return params;
	}
}
