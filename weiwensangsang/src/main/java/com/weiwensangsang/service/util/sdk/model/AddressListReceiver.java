package com.weiwensangsang.service.util.sdk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.weiwensangsang.service.util.sdk.config.Config;
import com.weiwensangsang.service.util.sdk.exception.ReceiverException;

/**
 * 邮件列表收件人
 * 
 * @author SendCloud
 *
 */
public class AddressListReceiver implements Receiver {

	public boolean useAddressList() {
		return true;
	}

	/**
	 * 地址列表
	 */
	private List<String> invokeNames = new ArrayList<String>();

	public List<String> getInvokeNames() {
		return invokeNames;
	}

	/**
	 * 增加地址列表的调用名称
	 * 
	 * @param to
	 */
	public void addTo(String to) {
		invokeNames.addAll(Arrays.asList(to.split(";")));
	}

	public boolean validate() throws ReceiverException {
		if (CollectionUtils.isEmpty(invokeNames))
			throw new ReceiverException("地址列表为空");
		if (invokeNames.size() > Config.MAX_MAILLIST)
			throw new ReceiverException("地址列表超过上限");
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String address : invokeNames) {
			if (sb.length() > 0)
				sb.append(";");
			sb.append(address);
		}
		return sb.toString();
	}
}