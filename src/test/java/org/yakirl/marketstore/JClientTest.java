package org.yakirl.marketstore;


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
		String symbol = "TEST";
		
		// write
		WriteRequest writeRequest = new WriteRequest(symbol + "/1Min/OHLCV", 3);
		writeRequest.addDataColum("Epoch", epochs);
		writeRequest.addDataColum("Open", opens);
		writeRequest.addDataColum("Close", closes);
		client.write(writeRequest);
		
		// list
		List<String> remoteSymbols = client.listSymbols();
		assertTrue(remoteSymbols.contains(symbol));
		
		// query
		String[] symbols = {symbol};
		QueryRequest req = new QueryRequest(symbols, "1Min", "OHLCV");
		QueryResponse res = client.query(req);

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
	}
	
	@Test
	public void testWriteFail() throws Exception {
		JClient client = new JClient();

		long[] epochs = {1563887580L, 1563887640L, 1563887700L};
		float[] opens = {12.5f, 44.4f, 21.6f};
		float[] closes = {11.1f, 50.0f, 6.2f};
		String symbol = "TEST";
		
		WriteRequest writeRequest = new WriteRequest(symbol + "/1Min/OHLCV", 2);
		
		writeRequest.addDataColum("Epoch", epochs);
		
	}
	
}
