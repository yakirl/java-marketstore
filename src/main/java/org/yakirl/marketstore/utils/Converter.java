package org.yakirl.marketstore.utils;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Map;
import java.util.HashMap;

public class Converter {
	
	ByteOrder defaultOrder = ByteOrder.LITTLE_ENDIAN;
	
	private Map<String, Integer> typeSizes = new HashMap<String, Integer>() {{
		put("int", 4);
		put("long", 8);
		put("float", 4);
		put("double", 8);
	}};
	
	public Map<String, String> typeConverter = new HashMap<String, String>() {{
		put("int", "i4");
		put("long", "i8");
		put("float", "f4");
		put("double", "f8");				
	}};
	
	public byte[] toByteArray(Object arr) throws Exception {
		Class<?> _type = arr.getClass().getComponentType();
		int length = Array.getLength(arr);
		ByteBuffer byteBuffer1;
		byteBuffer1 = ByteBuffer.allocate(length * typeSizes.get(_type.getCanonicalName()));
        byteBuffer1.order(defaultOrder);
        if (int.class.isAssignableFrom(_type)) {       
	        byteBuffer1.asIntBuffer().put((int[])arr);	        	       	       
        } else if (long.class.isAssignableFrom(_type)) {
	        byteBuffer1.asLongBuffer().put((long[])arr);	        	       
        } else if (float.class.isAssignableFrom(_type)) {
	        byteBuffer1.asFloatBuffer().put((float[])arr);	        	       
        } else if (double.class.isAssignableFrom(_type)) {
	        byteBuffer1.asDoubleBuffer().put((double[])arr);	        	       
        } else {
        	throw new Exception(String.format("got array of unsupported type %s", _type));
        }
		return byteBuffer1.array();
	}
	
	public Object fromByteArray(byte[] arr, String typename) throws Exception {
		ByteBuffer wrapped = ByteBuffer.wrap(arr).order(defaultOrder);
		Object res = null;
		if (typename.equals("i4")) {
			IntBuffer intBuffer = wrapped.asIntBuffer();
			int[] data = new int[intBuffer.remaining()];
			wrapped.asIntBuffer().get(data);
			res = data;
		} else if (typename.equals("i8")) {
			LongBuffer longBuffer = wrapped.asLongBuffer();
			long[] data = new long[longBuffer.remaining()];
			wrapped.asLongBuffer().get(data);
			res = data;
		} else if (typename.equals("f4")) {
			FloatBuffer floatBuffer = wrapped.asFloatBuffer();
			float[] data = new float[floatBuffer.remaining()];
			wrapped.asFloatBuffer().get(data);
			res = data;
		} else if (typename.equals("f8")) {
			DoubleBuffer doubleBuffer = wrapped.asDoubleBuffer();
			double[] data = new double[doubleBuffer.remaining()];
			wrapped.asDoubleBuffer().get(data);
			res = data;
		} else {
			throw new Exception(String.format("got unexpecteds type ##%s##", typename));
		}
		return res;
	}
}
