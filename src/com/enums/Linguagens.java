package com.enums;

public enum Linguagens {
	JAVA(".java"),
	PHP(".php"),
	C_SHARP(".cs"),
	CPP(".cpp"),
	H(".h");
	
	private String extensao;
	
	Linguagens(String extensao) {
		this.extensao = extensao;
	}
	
	@Override
	public String toString() {
		return extensao;
	}

}
