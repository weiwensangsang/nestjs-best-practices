package com.weiwensangsang.service.util.sdk.model;

import java.io.InputStream;

/**
 * 附件
 * 
 * @author Administrator
 *
 */
public class Attachment {

	public InputStream content;
	public String name;

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Attachment(InputStream content, String name) {
		this.content = content;
		this.name = name;
	}
}