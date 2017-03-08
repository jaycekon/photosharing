package com.sherlochao.common;

public class CommonConstants {
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("photo.properties");

    public static final String IMG_SERVER = propertiesLoader.getProperty("img.server");
    public static final String FILE_BASEPATH = propertiesLoader.getProperty("file.basepath"); 
    
}
