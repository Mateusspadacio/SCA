package com.enums;

public enum Languages {
	JAVA(".java"),
	PHP(".php"),
	C_SHARP(".cs"),
	CPP(".cpp"),
	H(".h");
	
	private String extension;
	
	Languages(String extension) {
		this.extension = extension;
	}
	
	@Override
	public String toString() {
		return extension;
	}

}
