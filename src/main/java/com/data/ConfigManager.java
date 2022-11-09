package com.data;

import java.io.IOException;

public class ConfigManager {
	
	public ConfigReader getInstanceConfigReader() throws IOException {
		ConfigReader r=new ConfigReader();
		return r;
	

	}
	
	public static ConfigManager getconfigManager() {
		ConfigManager cm=new ConfigManager();
		return cm;
	}
	
   private ConfigManager() {
	// TODO Auto-generated constructor stub
}
}
