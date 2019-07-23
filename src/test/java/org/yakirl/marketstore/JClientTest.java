package org.yakirl.marketstore;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.yakirl.marketstore.JClient;
import org.yakirl.marketstore.requests.QueryRequest;
import org.yakirl.marketstore.requests.WriteRequest;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;

import java.util.Map;


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
        
	// @Test
	public void testBasicWrite() throws Exception {
		JClient client = new JClient();	
		long[] epochs = {1563887580L, 1563887640L, 1563887700L};
		float[] opens = {12.5f, 44.4f, 21.6f};
		float[] closes = {11.1f, 50.0f, 6.2f};	
		WriteRequest req = new WriteRequest("HPE/1Min/OHLCV", 3);
		req.addDataColum("Epoch", epochs);
		req.addDataColum("Open", opens);
		req.addDataColum("Close", closes);
		client.write(req);
	}
	
	@Test
	public void testBasicQuery() throws Exception {
		JClient client = new JClient();		
		//QueryRequest req = new QueryRequest({"HPE"}, "1Min", "OHLCV");
		String[] symbols = {"HPE"};
		QueryRequest req = new QueryRequest(symbols, "1Min", "OHLCV");
		Map<String, Object> res = client.query(req);
		LOG.info(res.toString());
	}
}
