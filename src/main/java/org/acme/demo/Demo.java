package org.acme.demo;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {
	
	private boolean logToFile;
	private boolean logToConsole;
	private boolean logToDatabase;
	private Map<String, Object> dbParams;
	private static Logger logger;
	private Statement stmt;

	public Demo(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			Map<String, Object> dbParamsMap) throws Exception {
		logger = Logger.getLogger("MyLog");
		
		this.dbParams = dbParamsMap;
		
		this.logToConsole = logToConsoleParam;
		
		this.logToFile = logToFileParam;
		if (logToFileParam && dbParamsMap.get("logFileFolder") == null) {
			throw new Exception("Missing path file folder");
		}
			
		this.logToDatabase = logToDatabaseParam;
		if (logToDatabaseParam) {
			if (dbParamsMap.get("dbms") == null || dbParamsMap.get("serverName") == null
					|| dbParamsMap.get("portNumber") == null || dbParamsMap.get("userName") == null
					|| dbParamsMap.get("password") == null) {
				throw new Exception("Missing parameters for connection");
			}
			Connection connection = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", dbParamsMap.get("userName"));
			connectionProps.put("password", dbParamsMap.get("password"));

			connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://"
					+ dbParams.get("serverName") + ":" + dbParams.get("portNumber") + "/", connectionProps);
			this.stmt = connection.createStatement();
		}
	}

	public void LogMessage(String messageText, int msgType)
			throws Exception {
		
		if (messageText != null && messageText.length() > 0 ) {
			
			messageText = messageText.trim();
			Timestamp ts = new Timestamp(new Date().getTime());
			String msgToLog = null;
			
			switch(msgType) {
				case MessageType.MESSAGE:
					msgToLog = "message " + ts + ": " + messageText;
					WriteLog(msgToLog, MessageType.MESSAGE);
					break;
				case MessageType.WARNING:
					msgToLog = "warning " + ts + ": " + messageText;
					WriteLog(msgToLog, MessageType.WARNING);
					break;
				case MessageType.ERROR:
					msgToLog = "error " + ts + ": " + messageText;
					WriteLog(msgToLog, MessageType.ERROR);
					break;
				default: 
					break;
			}
			
		} else {
			return;
		}
		
	}

	private void WriteLog(String msg, int type) throws SecurityException, IOException, SQLException {

		if (logToFile) {
			File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			logger.addHandler(new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt"));
			logger.log(Level.INFO, msg);
		}

		if (logToConsole) {
			logger.addHandler(new ConsoleHandler());
			logger.log(Level.INFO, msg);
		}

		if (logToDatabase) {
			stmt.executeUpdate("insert into Log_Values('" + msg + "', " + type + ")");
		}

	}
}
