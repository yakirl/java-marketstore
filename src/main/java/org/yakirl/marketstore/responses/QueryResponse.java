package org.yakirl.marketstore.responses;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;

import org.yakirl.marketstore.utils.Converter;

public class QueryResponse {
	Object[] data;
	String timezone;
	List<String> types;
	List<String> names;
	
	public QueryResponse() {		
	}
	
	public List<String> types() {
		return types;
	}
	
	public List<String> names() {
		return names;
	}
	
	public Object[] data() {
		return data;
	}
	
	public String timezone() {
		return timezone;
	}
	
	public static QueryResponse loadFromMap(Map<String, Object> map) throws Exception {
		QueryResponse res = new QueryResponse();
		Map<String, Object> mainResult = (Map<String, Object>)map.get("result");
		res.timezone = (String)mainResult.get("timezone");
		Map<String, Object> resultDict = ((List<? extends Map<String, Object>>) mainResult.get("responses")).get(0);
		Map<String, Object> result = (Map<String, Object>)resultDict.get("result");
		res.types = (List<String>) result.get("types");
		res.names = (List<String>) result.get("names");
		//byte[][] dataBytes = (byte[][])result.get("data");
		List<byte[]> dataBytes = (List<byte[]>)result.get("data");
		
		Converter converter = new Converter();	
		res.data = new Object[res.names.size()];
		int i; for( i = 0; i < res.names.size(); i++) {
			res.data[i] = converter.fromByteArray(dataBytes.get(i), res.types.get(i));			
		}
		return res;		
	}		
}
