package com.weiwensangsang.service.util.sdk.config;

/**
 * 发件身份认证
 * @author Administrator
 *
 */
public class Credential {

	private String apiUser;
	private String apiKey;

	public Credential(String apiUser, String apiKey) {
		this.apiUser = apiUser;
		this.apiKey = apiKey;
	}

	public String getApiUser() {
		return apiUser;
	}

	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}