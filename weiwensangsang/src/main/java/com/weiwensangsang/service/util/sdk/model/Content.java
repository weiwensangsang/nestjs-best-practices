package com.weiwensangsang.service.util.sdk.model;

import com.weiwensangsang.service.util.sdk.exception.ContentException;

public interface Content {
	public boolean useTemplate();

	public boolean validate() throws ContentException;
}