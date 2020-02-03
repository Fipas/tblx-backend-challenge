package io.techhublisbon.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Operator {

	@NotNull
	@NotBlank
	@Size(min = 2, max = 2)
	private String id;

	public Operator(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
