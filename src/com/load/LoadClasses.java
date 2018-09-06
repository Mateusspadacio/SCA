package com.load;

import java.util.ArrayList;
import java.util.List;

import com.c.securegaps.getsvspsp.GetsVSPSPCPP;
import com.c.securegaps.unsigned.UnsignedCPP;
import com.def.methods.DefaultMethods;
import com.enums.Languages;
import com.php.securegaps.sqlinjector.SQLInjectorPHP;
import com.php.securegaps.url.UrlPHP;
import com.securegaps.finalvariables.FinalVariablesJava;
import com.securegaps.passwordfield.PasswordFieldJava;
import com.securegaps.socketssl.SocketSSLJava;
import com.securegaps.sqlinjector.SQLInjectorJava;
import com.securegaps.unboxing.UnboxingJava;

public class LoadClasses {

	private List<DefaultMethods> classesInitialized;
	private List<DefaultMethods> javaGaps;
	private List<DefaultMethods> phpGaps;
	private List<DefaultMethods> cppGaps;
	
	public LoadClasses(){
	}
	
	public void loadLanguage(String extension) {
		if(Languages.JAVA.toString().equals(extension)) {
			loadingJavaGaps();
			return;
		}
		
		if (Languages.PHP.toString().equals(extension)) {
			loadingPHPGaps();
			return;
		}
		
		if (Languages.CPP.toString().equals(extension) || Languages.H.toString().equals(extension)) {
			loadingCPPGaps();
			return;
		}
		
	}
	
	private void loadingJavaGaps(){
		if (javaGaps == null) {
			javaGaps = new ArrayList<>();
			javaGaps.add(new PasswordFieldJava());
			javaGaps.add(new SocketSSLJava());
			javaGaps.add(new SQLInjectorJava());
			javaGaps.add(new UnboxingJava());
			javaGaps.add(new FinalVariablesJava());
		}
		
		classesInitialized = javaGaps;
	}
	
	private void loadingPHPGaps() {
		if (phpGaps == null) {
			phpGaps = new ArrayList<>();
			phpGaps.add(new SQLInjectorPHP());
			phpGaps.add(new UrlPHP());
		}
		
		classesInitialized = phpGaps;
	}
	
	
	private void loadingCPPGaps() {
		if (cppGaps == null) {
			cppGaps = new ArrayList<>();
			cppGaps.add(new GetsVSPSPCPP());
			cppGaps.add(new UnsignedCPP());
		}
		
		classesInitialized = cppGaps;
	}
	
	public List<DefaultMethods> getClassesInitialized(){
		return classesInitialized;
	}
	
}
