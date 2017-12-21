package com.weiwensangsang.service.util.sdk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.util.Asserts;

import com.weiwensangsang.service.util.sdk.config.Config;
import com.weiwensangsang.service.util.sdk.exception.SmsException;

import net.sf.json.JSONObject;

public class SendCloudSms {
	public Integer templateId;
	public Integer msgType = 0;
	public List<String> phone;
	public Map<String, String> vars;

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public List<String> getPhone() {
		return phone;
	}

	public String getPhoneString() {
		Asserts.notNull(phone, "phone");
		StringBuilder sb = new StringBuilder();
		for (String p : phone) {
			if (sb.length() > 0)
				sb.append(",");
			sb.append(p);
		}
		return sb.toString();
	}

	public Map<String, String> getVars() {
		return vars;
	}

	public String getVarsString() {
		Asserts.notNull(vars, "vars");
		return JSONObject.fromObject(vars).toString();
	}

	public void addPhone(String mobile) {
		if (CollectionUtils.isEmpty(phone)) {
			phone = new ArrayList<String>();
		}
		phone.add(mobile);
	}

	public void addVars(String key, String value) {
		if (MapUtils.isEmpty(vars)) {
			vars = new HashMap<String, String>();
		}
		vars.put(key, value);
	}

	public boolean validate() throws SmsException {
		if (templateId == null)
			throw new SmsException("模版为空");
		if (CollectionUtils.isEmpty(phone))
			throw new SmsException("收信人为空");
		if (phone.size() > Config.MAX_RECEIVERS)
			throw new SmsException("收信人超过限制");
		return true;
	}
}