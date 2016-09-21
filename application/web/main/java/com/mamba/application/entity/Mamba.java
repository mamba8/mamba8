package com.mamba.application.entity;

import com.jztey.framework.mvc.Id;

//@Entity
//@Table(name = "")
public class Mamba implements Id {

	private Long id;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
