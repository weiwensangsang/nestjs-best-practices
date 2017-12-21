package com.weiwensangsang.service.util.sdk.model;

import org.apache.commons.lang.StringUtils;

import com.weiwensangsang.service.util.sdk.exception.ContentException;

/**
 * 模版发送
 * 
 * <pre>
 * 通过已上传的模版发送邮件
 * </pre>
 * 
 * @author Administrator
 *
 */
public class TemplateContent implements Content {

	/**
	 * 是否使用模版发送
	 */
	public boolean useTemplate() {
		return true;
	}

	/**
	 * 模版名称
	 */
	private String templateInvokeName;

	/**
	 * 返回模版名称
	 * 
	 * @return 模版名称
	 */
	public String getTemplateInvokeName() {
		return templateInvokeName;
	}

	/**
	 * 设置模版名称
	 * 
	 * @param templateInvokeName
	 */
	public void setTemplateInvokeName(String templateInvokeName) {
		this.templateInvokeName = templateInvokeName;
	}

	public boolean validate() throws ContentException {
		if (StringUtils.isBlank(templateInvokeName))
			throw new ContentException("模版为空");
		return true;
	}

}