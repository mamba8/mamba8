package com.jztey.framework.mvc;

import java.util.ArrayList;
import java.util.List;

public class RestfulResult<T> {
	private boolean success = true;
	private int code = 0;
	private String message = "";
	private List<T> data = new ArrayList<>();

	public RestfulResult() {
	}

	public RestfulResult(T t) {
		this();
		this.addData(t);
	}

	public RestfulResult(List<T> data) {
		this();
		this.addData(data);
	}

	public void addData(T t) {
		this.data.add(t);
	}

	public void addData(List<T> data) {
		this.data.addAll(data);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getData() {
		return data;
	}
}
