package com.report;

import java.util.ArrayList;

import com.c.securegaps.getsvspsp.GetsVSPSPCPP;
import com.c.securegaps.unsigned.UnsignedCPP;
import com.def.methods.DefaultMethods;
import com.php.securegaps.sqlinjector.SQLInjectorPHP;
import com.php.securegaps.url.UrlPHP;
import com.securegaps.finalvariables.FinalVariablesJava;
import com.securegaps.passwordfield.PasswordFieldJava;
import com.securegaps.socketssl.SocketSSLJava;
import com.securegaps.sqlinjector.SQLInjectorJava;
import com.securegaps.unboxing.UnboxingJava;

public class SendDataReport {
	
	private ReportAdapter ra;
	
	public SendDataReport(ReportAdapter ra) {
		this.ra = ra;	
	}
	
	private ArrayList<String> returnList(DefaultMethods obj) {
		ArrayList<String> list = null;
		try {
			list = obj.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list == null) {
			return new ArrayList<String>();
		}

		return list;
	}
	
	/*
	 * ANEXAR MAIS UM IF ELSE QUANDO MAIS ERRORS 
	 * FOREM IMPLEMENTADOS NO SISTEMA
	 * 
	 */

	protected void verifyData(DefaultMethods obj) {
		verifyJava(obj);
		verifyPHP(obj);
		verifyCPP(obj);
	}
	
	private void verifyJava(DefaultMethods obj) {
		if (obj instanceof PasswordFieldJava) {
			ra.setPasswordFieldGaps(returnList(obj));
		} else if (obj instanceof SocketSSLJava) {
			ra.setSocketGaps(returnList(obj));
		} else if (obj instanceof SQLInjectorJava) {
			ra.setSqlGaps(returnList(obj));
		} else if (obj instanceof UnboxingJava) {
			ra.setUnboxingGaps(returnList(obj));
		}else if (obj instanceof FinalVariablesJava) {
			ra.setFinalVariablesGaps(returnList(obj));
		}
	}
	
	private void verifyPHP(DefaultMethods obj) {
		if (obj instanceof SQLInjectorPHP) {
			ra.setSqlPHPGaps(returnList(obj));
		} else if (obj instanceof UrlPHP) {
			ra.setUrlGaps(returnList(obj));
		}
	}
	
	private void verifyCPP(DefaultMethods obj) {
		if (obj instanceof GetsVSPSPCPP) {
			ra.setGetsGaps(returnList(obj));
		} else if(obj instanceof UnsignedCPP) {
			ra.setUnsignedGaps(returnList(obj));
		}
	}

}
