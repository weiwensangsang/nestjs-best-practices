package com.weiwensangsang.service.util.sdk.model;

import com.weiwensangsang.service.util.sdk.exception.ReceiverException;

public interface Receiver {
	public boolean useAddressList();
	
	public boolean validate() throws ReceiverException;
	
	public String toString();
}