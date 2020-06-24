package org.yakirl.marketstore.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class Common {
	
	private static List<String> timeframes = new ArrayList<String>(Arrays.asList(
	    "1Min"
	));
	
	public static String tbk(String symbol, String timeframe, String attrGroup) {
		return tbks(new String[] {symbol}, timeframe, attrGroup);
	}
	
	public static String tbks(String[] symbols, String timeframe, String attrGroup) {
		return String.format("%s/%s/%s", String.join(",", symbols), timeframe, attrGroup);
	}
	
	private void validateTimeframe(String timeframe) throws Exception {
		if (!timeframes.contains(timeframe)) {
			throw new Exception(String.format("unsupported timeframe %s", timeframe));
		}
	}
}
