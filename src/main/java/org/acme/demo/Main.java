package org.acme.demo;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Map params = new HashMap<String, Object>();
		//Especificar en el map la conexion a bd o nombre de folder
		
		Demo d = new Demo(false, true, false, params);
		
		String messageText = "mensaje de prueba";
		d.LogMessage(messageText, MessageType.MESSAGE);
		d.LogMessage(messageText, MessageType.WARNING);
		d.LogMessage(messageText, MessageType.ERROR);
		
	}

}

