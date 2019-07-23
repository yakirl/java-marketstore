package org.yakirl.marketstore.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryRequest {
	private String tbk;
	// private Map<String, Object> dataset = new HashMap<String, Object>();
	private Map<String, String> typeConverter;
	Long epochStart;
	Long epochEnd;
	Integer limit;
	boolean limitFromStart;
	
	public QueryRequest(String[] symbols, String timeframe, String attrGroup) {
		tbk = String.format("%s/%s/%s", String.join(",", symbols), timeframe, attrGroup);		
	}
	
	public QueryRequest epochStart(long epochStart) {
		this.epochStart = epochStart;
		return this;
	}
	
	public QueryRequest epochEnd(long epochEnd) {
		this.epochEnd = epochEnd;
		return this;
	}
	
	public QueryRequest limit(int limit) {
		this.limit = limit;
		return this;
	}
	
	public QueryRequest limitFromStart(boolean limitFromStart) {
		this.limitFromStart = limitFromStart;
		return this;
	}
	
	public Map<String, Object> getAsMap() {
				
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("destination", tbk);
		
		req.put("epoch_start", epochStart);
		req.put("epoch_end", epochEnd);
		req.put("limit_record_count", limit);
		req.put("limit_from_start", limitFromStart);
		
		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();
		requests.add(req);
		
		Map<String, List<Map<String, Object>>> params = new HashMap<String, List<Map<String, Object>>>();
		params.put("requests", requests);
		
		Map<String, Object> map  = new HashMap<String, Object>();;
		map.put("id", "1");
		map.put("jsonrpc", "2.0");
		map.put("method", "DataService.Query");	
		map.put("params", params);	
		System.out.println(map.toString());
		return map;
	}
}
