package org.acme.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Value("${database.url}")
	private String dbUrl;
	
	@Value("${database.username}")
	private String dbUser;
	
	@Value("${database.password}")
	private String dbPass;
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
    void testDefault() throws Exception {
		Map params = new HashMap<String, Object>();
		//Especificar en el map la conexion a bd o nombre de folder
		
		Demo d = new Demo(false, true, false, params);
		
		String messageText = "mensaje de prueba";
		d.LogMessage(messageText, MessageType.MESSAGE);
		d.LogMessage(messageText, MessageType.WARNING);
		d.LogMessage(messageText, MessageType.ERROR);
		
    }
	
    @Test
    @Disabled
    void disabledTest() {
        assertTrue(false);
    }
	

}
