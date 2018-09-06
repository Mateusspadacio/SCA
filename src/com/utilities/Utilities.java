package com.utilities;

public class Utilities {
	
	public static String getFileExtension(String path) {
		return path.substring(path.lastIndexOf("."), path.length());
	}

}
