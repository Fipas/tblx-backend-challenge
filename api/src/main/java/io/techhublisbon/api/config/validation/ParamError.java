package io.techhublisbon.api.config.validation;

public class ParamError {
	
	private String parameter;
	private String error;

	public ParamError(String parameter, String error) {
		this.parameter = parameter;
		this.error = error;
	}

	public String getParameter() {
		return parameter;
	}

	public String getError() {
		return error;
	}
}