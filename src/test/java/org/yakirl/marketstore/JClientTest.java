package org.yakirl.marketstore;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.yakirl.marketstore.JClient;
import org.yakirl.marketstore.requests.QueryRequest;
import org.yakirl.marketstore.requests.WriteRequest;
import org.yakirl.marketstore.responses.QueryResponse;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;



public class JClientTest {

	protected static final Logger LOG = LogManager.getLogger(JClientTest.class);
    
	@Before
    public void setUp() throws Exception {
    	LOG.info("setUp test");
    }
    
	@After
    public void tearDown() {
    	LOG.info("tearDown test");
    }   
        
	@Test
	public void testBasicWriteQuery() throws Exception {
		JClient client = new JClient();
		// test data
		long[] epochs = {1563887580L, 1563887640L, 1563887700L};
		float[] opens = {12.5f, 44.4f, 21.6f};
		float[] closes = {11.1f, 50.0f, 6.2f};
		String symbol = RandomStringUtils.random(4, true, false);
		
		// write
		WriteRequest writeRequest = new WriteRequest(symbol, "1Min", "OHLCV", 3);
		writeRequest.addDataColum("Epoch", epochs);
		writeRequest.addDataColum("Open", opens);
		writeRequest.addDataColum("Close", closes);
		client.write(writeRequest);
		
		// list
		List<String> remoteSymbols = client.listSymbols();
		assertTrue(remoteSymbols.contains(symbol));
		
		// query
		String[] symbols = {symbol};
		QueryRequest queryReq = new QueryRequest(symbols, "1Min", "OHLCV");
		QueryResponse res = client.query(queryReq);

		// validate
		long[] epochsRes = (long[])res.data()[0];
		int i; for (i = 0; i < epochsRes.length; i++) {
			assertEquals(epochsRes[i], epochs[i]);
		}		
		float[] opensRes = (float[])res.data()[1];
		for (i = 0; i < opensRes.length; i++) {
			assertEquals(opensRes[i], opens[i], 0.001);
		}		
		float[] closesRes = (float[])res.data()[2];
		for (i = 0; i < closesRes.length; i++) {
			assertEquals(closesRes[i], closes[i], 0.001);
		}
		
		// limited query
		QueryRequest limitedQueryReq = (new QueryRequest(symbols, "1Min", "OHLCV")).limit(1);
		res = client.query(limitedQueryReq);
		assertEquals(((long[])res.data()[0]).length, 1);
		
		/** Destroy doesnt work. add when fixed
		// delete
		client.destroy(symbol + "/1Min/OHLCV");
		remoteSymbols = client.listSymbols();
		assertFalse(String.format("destroy failed, %s still exists on server", symbol), remoteSymbols.contains(symbol));
		**/
	}
	
	@Test
	public void testWriteFail() throws Exception {
		JClient client = new JClient();

		long[] epochs = {1563887580L, 1563887640L, 1563887700L};
		float[] opens = {12.5f, 44.4f, 21.6f};
		float[] closes = {11.1f, 50.0f, 6.2f};
		String symbol = RandomStringUtils.random(4, true, false);		
		
		try {
			WriteRequest badRequest = new WriteRequest(symbol, "1Min", "OHLCV", 2);
			badRequest.addDataColum("Epoch", epochs);
			throw new Exception("expected bad array size excpetion");
		} catch(Exception e) {
		}
		
		try {
			WriteRequest badRequest = new WriteRequest(symbol, "1Min", "OHLCV", 2);			
			client.write(badRequest);
			throw new Exception("expected server error - no data");
		} catch(Exception e) {
		}
		
		WriteRequest writeRequest = new WriteRequest(symbol, "1Min", "OHLCV", 3);
		writeRequest.addDataColum("Open", opens);
		try {
			client.write(writeRequest);
			throw new Exception("expected server error - no Epoch");
		} catch(Exception e) {			
		}
		
	}
	
}
