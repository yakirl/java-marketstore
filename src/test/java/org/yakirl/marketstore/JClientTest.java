package org.yakirl.marketstore;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.yakirl.marketstore.JClient;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;


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
	public void testBasic() throws Exception {
		
	}	
}
