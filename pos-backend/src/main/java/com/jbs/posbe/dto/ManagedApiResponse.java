package com.jbs.posbe.dto;

import java.time.LocalDateTime;

public class ManagedApiResponse<T> {
	private int status;
	private String message;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();
	public ManagedApiResponse() {
		super();
	}
	public ManagedApiResponse(int status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
