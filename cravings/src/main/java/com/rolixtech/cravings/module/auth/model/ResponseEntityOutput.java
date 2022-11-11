package com.rolixtech.cravings.module.auth.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



public class ResponseEntityOutput  {

	
	public String CODE;
	public String SYSTEM_MESSAGE;
	public String USER_MESSAGE ;
	public String ERROR_TRACE ;
	public List<Map> DATA;
	
	
	
	public ResponseEntityOutput(String cODE, String sYSTEM_MESSAGE, String uSER_MESSAGE, String eRROR_TRACE,
			List<Map> dATA) {
		super();
		CODE = cODE;
		SYSTEM_MESSAGE = sYSTEM_MESSAGE;
		USER_MESSAGE = uSER_MESSAGE;
		ERROR_TRACE = eRROR_TRACE;
		DATA = dATA;
	}
	
	



	public ResponseEntityOutput() {
		super();
	}





	@Override
	public String toString() {
		return "TransactionResponse [CODE=" + CODE + ", SYSTEM_MESSAGE=" + SYSTEM_MESSAGE + ", USER_MESSAGE="
				+ USER_MESSAGE + ", ERROR_TRACE=" + ERROR_TRACE + "]";
	}



	public void sendRedirect(String string) {
		CODE="403";
		
	}
	
	
	
	
	
	

	
}