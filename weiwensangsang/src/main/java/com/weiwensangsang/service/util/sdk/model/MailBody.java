package com.weiwensangsang.service.util.sdk.model;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.weiwensangsang.service.util.sdk.exception.BodyException;

import net.sf.json.JSONObject;

/**
 * 邮件主体，包含发件人、主题以及头部、扩展字段
 *
 * <P>
 * <OL>
 * <LI><B>from</B> 发件人地址，必须
 * <LI><B>subject</B> 邮件主题，必须
 * <LI><B>fromName</B> 发件人名称，非必须
 * <LI><B>replyTo</B> 邮件回复地址，非必须
 * <LI><B>labelId</B> 标签ID，非必须
 * <LI><B>headers</B> 头部信息，非必须
 * <LI><B>attachments</B> 附件，非必须
 * <LI><B>xsmtpapi</B> 扩展字段，非必须
 * </OL>
 * <P>
 * 
 * @author Administrator
 *
 */
public class MailBody {

	/**
	 * 发件人地址
	 */
	private String from;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 发件人名称
	 */
	private String fromName;
	/**
	 * 用户默认的回复邮件地址
	 */
	private String replyTo;
	/**
	 * 本次发送所使用的标签ID
	 */
	private Integer labelId;
	/**
	 * 邮件头部信息
	 */
	private Map<String, String> headers;
	/**
	 * 邮件附件
	 */
	private List<Object> attachments;
	/**
	 * <pre>
	 * SMTP 扩展字段 X-SMTPAPI 是 SendCloud 为开发者提供的邮件个性化定制的处理方式, 开发者通过这个特殊的 信头扩展字段,
	 * 可以设置邮件处理方式的很多参数.
	 * 
	 * SMTP 调用时, 开发者可以在邮件中自行插入各种头域信息, 这是 SMTP 协议所允许的. 而 SendCloud 会检索 key 为
	 * X-SMTPAPI 的头域信息, 如果发现含有此头域, 则其 value 的值可以被解析, 用来改变邮件的处理方式.
	 * </pre>
	 */
	private Map<String, Object> xsmtpapi;

	/**
	 * 添加邮件头部信息
	 * 
	 * @param key
	 * @param value
	 */
	public void addHeader(String key, String value) {
		if (headers == null)
			headers = new HashMap<String, String>();
		headers.put(key, value);
	}

	/**
	 * 添加附件
	 * 
	 * @param file
	 */
	public void addAttachments(File file) {
		if (attachments == null)
			attachments = new ArrayList<Object>();
		attachments.add(file);
	}

	/**
	 * 添加附件
	 * 
	 * @param stream
	 * @param name
	 */
	public void addAttachments(InputStream stream, String name) {
		if (attachments == null)
			attachments = new ArrayList<Object>();
		attachments.add(new Attachment(stream, name));
	}

	/**
	 * 添加xsmtpapi
	 * 
	 * @param key
	 * @param value
	 */
	public void addXsmtpapi(String key, Object value) {
		if (xsmtpapi == null)
			xsmtpapi = new HashMap<String, Object>();
		xsmtpapi.put(key, value);
	}

	public String getXsmtpapiString() {
		return JSONObject.fromObject(xsmtpapi).toString();
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHeadersString() {
		return JSONObject.fromObject(headers).toString();
	}

	public String getFrom() {
		return from;
	}

	/**
	 * 设置发件人
	 * 
	 * @param from
	 *            <p>
	 *            格式：support@ifaxin.com, 爱发信支持&lt;support@ifaxin.com&gt;
	 *            </p>
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getXsmtpapi() {
		return xsmtpapi;
	}

	/**
	 * 验证邮件是否完整
	 */
	public boolean validate() throws BodyException {
		if (StringUtils.isBlank(from))
			throw new BodyException("发件人为空");
		if (StringUtils.isBlank(subject))
			throw new BodyException("邮件主题为空");
		return true;
	}
}