package com.mamba.framework.mvc;

import java.util.List;

public class RestfulPagingResult<T> extends RestfulResult<T> {
	private int total = 0;

	public RestfulPagingResult(List<T> data, int total) {
		super(data);
		this.total = total;
	}

	public RestfulPagingResult(List<T> data, int total, boolean success, String message) {
		super(data);
		this.total = total;
		super.getMessage();
		super.isSuccess();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
