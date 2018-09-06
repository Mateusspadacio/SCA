package com.enums;

public enum GapsDescription {
	JAVA_PASSWORDFIELD("_JPASSWORDFIELD"),
	JAVA_SOCKETSSL("_JSOCKETSSL"),
	JAVA_SQLINJECTION("_JSQLINJECTION"),
	JAVA_UNBOXING("_JUNBOXING"),
	JAVA_VARIAVEISFINAL("_JVARIAVEISFINAL"),
	PHP_SQLINJECTION("_PSQLINJECTION"),
	PHP_URL("_PURL"),
	CPP_GETSVSPSP("_CGETSVSPSP"),
	CPP_UNSIGNED("_UNSIGNED");
	
	private String desc;
	
	GapsDescription(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
