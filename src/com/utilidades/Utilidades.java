package com.utilidades;

public class Utilidades {
	
	public static String getExtensaoArquivo(String path) {
		return path.substring(path.lastIndexOf("."), path.length());
	}

}
