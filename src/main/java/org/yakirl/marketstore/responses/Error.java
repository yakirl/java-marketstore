package org.yakirl.marketstore.responses;

import java.util.Map;

/****
 *  See: https://github.com/alpacahq/marketstore/blob/master/utils/rpc/msgpack2/error.go
 * 
 * for client errors raise exceptions.
 */

public class Error {
	
	private int code;
	private String message;
	
	public Error(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int code() {
		return code;
	}
	
	public String message() {
		return message;
	}
	
	public static Error processResponse(Map<String, Object> response) throws Exception {
		
		Object err = response.get("error");
		if (err == null) {
			return null;
		}
		Map<String, Object> errMap = (Map<String, Object>)err;
		switch ((int)errMap.get("code")) {
		case -32700:
		case -32601:
		case -32602:
			throw new Exception(errMap.toString());
		}
		return new Error((int)errMap.get("code"), (String)errMap.get("message"));
	}
}
