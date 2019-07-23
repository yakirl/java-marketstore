package org.yakirl.marketstore.requests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yakirl.marketstore.utils.Converter;


public class WriteRequest {

	private int length;
	private String tbk;
	// private Map<String, Object> dataset = new HashMap<String, Object>();
	private List<byte[]> dataList;
	private List<String> names;
	private List<String> types;
	private Map<String, String> typeConverter; 
	
	public WriteRequest(String tbk, int numOfRecords) {
		this.tbk = tbk;
		length = numOfRecords;
		names = new ArrayList<String>();
		dataList = new ArrayList<byte[]>();
		names = new ArrayList<String>();
		types = new ArrayList<String>();
		typeConverter = new HashMap<String, String>() {{
				put("int", "i4");
				put("long", "i8");
				put("float", "f4");
				put("double", "f8");				
		}};
	}
	
	public void addDataColum(String name, Object col) throws Exception {
		if (!col.getClass().isArray()) {
			throw new Exception("only arrays should be given");
		}
		int _length = Array.getLength(col);
		if (_length != length) {
			throw new Exception(String.format("size of array %d differnts from initialized size  %d",
					Array.getLength(col), length));
		}
		Converter converter = new Converter();
		dataList.add(converter.toByteArray(col));
		names.add(name);
		Class<?> _type = col.getClass().getComponentType();
		types.add(typeConverter.get(_type.getCanonicalName()));
	}
	
	private byte[][] convertData(List<byte[]> dataList) {
		byte[][] data = new byte[dataList.size()][];
		int i; for (i = 0; i < dataList.size(); i++) {
			data[i] = dataList.get(i);
		}
		return data;
	}
	
	public Map<String, Object> getAsMap() {

		byte[][] data = convertData(dataList);
		Map<String, Object> dataset = new HashMap<String, Object>();
		dataset.put("data", data);
		dataset.put("names", names);
		dataset.put("types", types);
		dataset.put("lengths", new HashMap<String, Integer>(){{put(tbk, length);}});
		dataset.put("startindex", new HashMap<String, Integer>(){{put(tbk, 0);}});
		dataset.put("length", length);
		
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("dataset", dataset);
		req.put("is_variable_length", false);

		List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();
		requests.add(req);
		
		Map<String, List<Map<String, Object>>> params = new HashMap<String, List<Map<String, Object>>>();
		params.put("requests", requests);
		
		Map<String, Object> map  = new HashMap<String, Object>();;
		map.put("id", "1");
		map.put("jsonrpc", "2.0");
		map.put("method", "DataService.Write");	
		map.put("params", params);	
		System.out.println(map.toString());
		return map;
	}
}
