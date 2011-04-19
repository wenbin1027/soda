package com.ade.restapi;

import com.ade.parser.Parser;

public abstract class ApiInterface {
	protected Parser parser;

	public ApiInterface(Parser parser){
		this.parser=parser;
	}
	
	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}
}
