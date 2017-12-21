package com.weiwensangsang.service.util.sdk.exception;

public class BodyException extends Throwable implements SCException {
	private static final long serialVersionUID = 1L;
	private static final int errorCode = 303;

	public BodyException(String message) {
		super(message);
	}

	public String getMessage() {
		return String.format("code:%d,message:%s", errorCode, super.getMessage());
	}
}
