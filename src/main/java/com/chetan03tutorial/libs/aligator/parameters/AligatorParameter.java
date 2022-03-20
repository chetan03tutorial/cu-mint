package com.chetan03tutorial.libs.aligator.parameters;


public interface AligatorParameter {
	void prepareContext(RequestContext request, String cellText);
	String getMnemonic();
}
