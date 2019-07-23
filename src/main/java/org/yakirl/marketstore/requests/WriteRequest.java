package org.yakirl.marketstore.requests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yakirl.marketstore.utils.Converter;


public class WriteRequest extends Request {

	private int length;
	private String tbk;
	private List<byte[]> dataList;
	private List<String> names;
	private List<String> types;
	private Converter converter;
	
	
	public WriteRequest(String tbk, int numOfRecords) {
		super("Write");
		this.tbk = tbk;
		length = numOfRecords;
		names = new ArrayList<String>();
		dataList = new ArrayList<byte[]>();
		names = new ArrayList<String>();
		types = new ArrayList<String>();
		converter = new Converter();
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
		dataList.add(converter.toByteArray(col));
		names.add(name);
		Class<?> _type = col.getClass().getComponentType();
		types.add(converter.typeConverter.get(_type.getCanonicalName()));
	}
	
	private byte[][] convertData(List<byte[]> dataList) {
		byte[][] data = new byte[dataList.size()][];
		int i; for (i = 0; i < dataList.size(); i++) {
			data[i] = dataList.get(i);
		}
		return data;
	}
	
	public Map<String, List<Map<String, Object>>> getParams() {

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
		
		return params;
	}
}
