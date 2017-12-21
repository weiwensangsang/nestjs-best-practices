package com.weiwensangsang.service.util.sdk.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;

import com.weiwensangsang.service.util.sdk.config.Config;
import com.weiwensangsang.service.util.sdk.config.Credential;
import com.weiwensangsang.service.util.sdk.exception.SmsException;
import com.weiwensangsang.service.util.sdk.exception.VoiceException;
import com.weiwensangsang.service.util.sdk.model.Attachment;
import com.weiwensangsang.service.util.sdk.model.MailAddressReceiver;
import com.weiwensangsang.service.util.sdk.model.SendCloudMail;
import com.weiwensangsang.service.util.sdk.model.SendCloudSms;
import com.weiwensangsang.service.util.sdk.model.SendCloudVoice;
import com.weiwensangsang.service.util.sdk.model.TemplateContent;
import com.weiwensangsang.service.util.sdk.model.TextContent;
import com.weiwensangsang.service.util.sdk.model.TextContent.ScContentType;
import com.weiwensangsang.service.util.sdk.util.Md5Util;
import com.weiwensangsang.service.util.sdk.util.ResponseData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 
 * 发送邮件代码示例
 * <p>
 * <blockquote>
 * 
 * <pre>
 * SendCloud sc = SendCloudBuilder.build();
 * 
 * // 创建邮件body
 * MailBody body = new MailBody();
 * body.setFrom("test@163.com");
 * body.setFromName("张三");
 * body.setReplyTo("service@qq.com");
 * body.setSubject("测试");
 * // 创建文件附件
 * body.addAttachments(new File("D:/test.txt"));
 * // 创建流附件
 * body.addAttachments(new FileInputStream(new File("D:/ff.png")),"ff.png");
 * // 邮箱收件人
 * MailAddressReceiver receiver = new MailAddressReceiver();
 * receiver.setBroadcastSend(true);// 广播发送(收件人会全部显示)
 * receiver.addTo("1234@qq.com");
 * 
 * // 地址列表收件人
 * // MailListReceiver receiver=new MailListReceiver();
 * // 添加邮件地址列表
 * // receiver.addMailList("developers@sendcloud.com");
 * 
 * // 创建模版邮件内容
 * TemplateContent content = new TemplateContent();
 * content.setTemplateInvokeName("templateInvokeName");
 * 
 * // 创建文本邮件内容
 * // TextContent content = new TextContent();
 * // content.setContent_type(ScContentType.html);
 * // content.setText("hello world");
 * 
 * // 创建邮件
 * SendCloudMail scmail = new SendCloudMail();
 * scmail.setBody(body);
 * scmail.setContent(content);
 * scmail.setTo(receiver);
 * 
 * // 发信
 * ResponseData result = sc.sendMail(scmail);
 * System.out.println(JSONObject.fromObject(result).toString());
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * 发送短信代码示例
 * 
 * 
 * <p>
 * <blockquote>
 * 
 * <pre>
 * SendCloud sc = SendCloudBuilder.build();
 * 
 * SendCloudSms sms = new SendCloudSms();
 * sms.setTemplateId(65825);
 * sms.addPhone("13512345678");
 * sms.addVars("code", "123456");
 * 
 * ResponseData result = sc.sendSms(sms);
 * 
 * System.out.println(JSONObject.fromObject(result).toString());
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * 发送语音代码示例
 * <p>
 * <blockquote>
 * 
 * <pre>
 * SendCloud sc = SendCloudBuilder.build();
 * 
 * SendCloudVoice sms = new SendCloudVoice();
 * sms.setPhone("13312345678");
 * sms.setCode("1234");
 * 
 * ResponseData result = sc.sendVoice(sms);
 * 
 * System.out.println(JSONObject.fromObject(result).toString());
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * @author SendCloud
 *
 */
public class SendCloud {

	private String server;
	private String mailAPI;
	private String templateAPI;
	private String smsAPI;
	private String voiceAPI;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getMailAPI() {
		return mailAPI;
	}

	public void setMailAPI(String mailAPI) {
		this.mailAPI = mailAPI;
	}

	public String getTemplateAPI() {
		return templateAPI;
	}

	public void setTemplateAPI(String templateAPI) {
		this.templateAPI = templateAPI;
	}

	public String getSmsAPI() {
		return smsAPI;
	}

	public void setSmsAPI(String smsAPI) {
		this.smsAPI = smsAPI;
	}

	public String getVoiceAPI() {
		return voiceAPI;
	}

	public void setVoiceAPI(String voiceAPI) {
		this.voiceAPI = voiceAPI;
	}

	/**
	 * 发送邮件
	 * 
	 * @param credential
	 *            身份认证
	 * @param mail
	 *            邮件
	 */
	public ResponseData sendMail(SendCloudMail mail) throws Throwable {
		Asserts.notNull(mail, "mail");
		Asserts.notBlank(Config.api_user, "api_user");
		Asserts.notBlank(Config.api_key, "api_key");
		mail.validate();
		Credential credential = new Credential(Config.api_user, Config.api_key);
		if (CollectionUtils.isEmpty(mail.getBody().getAttachments())) {
			return post(credential, mail);
		} else {
			return multipartPost(credential, mail);
		}
	}

	/**
	 * 普通方式发送
	 * 
	 * @param credential
	 * @param mail
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private ResponseData post(Credential credential, SendCloudMail mail) throws ClientProtocolException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apiUser", credential.getApiUser()));
		params.add(new BasicNameValuePair("apiKey", credential.getApiKey()));
		params.add(new BasicNameValuePair("from", mail.getBody().getFrom()));
		params.add(new BasicNameValuePair("fromName", mail.getBody().getFromName()));
		params.add(new BasicNameValuePair("subject", mail.getBody().getSubject()));
		params.add(new BasicNameValuePair("replyTo", mail.getBody().getReplyTo()));
		if (mail.getBody().getLabelId() != null)
			params.add(new BasicNameValuePair("labelId", mail.getBody().getLabelId().toString()));

		/**
		 * 是否使用模版发送
		 */
		if (mail.getContent().useTemplate()) {
			TemplateContent content = (TemplateContent) mail.getContent();
			params.add(new BasicNameValuePair("templateInvokeName", content.getTemplateInvokeName()));
		} else {
			TextContent content = (TextContent) mail.getContent();
			if (content.getContent_type().equals(ScContentType.html)) {
				params.add(new BasicNameValuePair("html", content.getText()));
			} else {
				params.add(new BasicNameValuePair("plain", content.getText()));
			}
		}
		/**
		 * 是否使用地址列表
		 */
		if (mail.getTo() != null) {
			if (mail.getTo().useAddressList()) {
				params.add(new BasicNameValuePair("useAddressList", "true"));
				params.add(new BasicNameValuePair("to", mail.getTo().toString()));
			} else {
				MailAddressReceiver receiver = (MailAddressReceiver) mail.getTo();
				if (!mail.getContent().useTemplate() && receiver.isBroadcastSend()) {
					params.add(new BasicNameValuePair("to", receiver.toString()));
					params.add(new BasicNameValuePair("cc", receiver.getCcString()));
					params.add(new BasicNameValuePair("bcc", receiver.getBccString()));
				} else {
					if (mail.getBody().getXsmtpapi() != null && !mail.getBody().getXsmtpapi().containsKey("to")) {
						mail.getBody().addXsmtpapi("to", JSONArray.fromObject(receiver.getTo()));
					}
				}
			}
		}
		if (MapUtils.isNotEmpty(mail.getBody().getHeaders()))
			params.add(new BasicNameValuePair("headers", mail.getBody().getHeadersString()));
		if (MapUtils.isNotEmpty(mail.getBody().getXsmtpapi()))
			params.add(new BasicNameValuePair("xsmtpapi", mail.getBody().getXsmtpapiString()));
		params.add(new BasicNameValuePair("respEmailId", "true"));
		params.add(new BasicNameValuePair("useNotification", "false"));

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(mail.getContent().useTemplate() ? templateAPI : mailAPI);
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		ResponseData result = validate(response);
		httpPost.releaseConnection();
		httpclient.close();
		return result;
	}

	/**
	 * multipart方式发送
	 * 
	 * @param credential
	 * @param mail
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private ResponseData multipartPost(Credential credential, SendCloudMail mail)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(mail.getContent().useTemplate() ? templateAPI : mailAPI);
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		MultipartEntityBuilder entity = MultipartEntityBuilder.create();
		entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		entity.setCharset(Charset.forName("UTF-8"));
		ContentType TEXT_PLAIN = ContentType.create("text/plain", Charset.forName("UTF-8"));
		entity.addTextBody("apiUser", credential.getApiUser(), TEXT_PLAIN);
		entity.addTextBody("apiKey", credential.getApiKey(), TEXT_PLAIN);
		entity.addTextBody("from", mail.getBody().getFrom(), TEXT_PLAIN);
		if (StringUtils.isNotEmpty(mail.getBody().getFromName()))
			entity.addTextBody("fromName", mail.getBody().getFromName(), TEXT_PLAIN);
		entity.addTextBody("subject", mail.getBody().getSubject(), TEXT_PLAIN);
		if (StringUtils.isNotEmpty(mail.getBody().getReplyTo()))
			entity.addTextBody("replyTo", mail.getBody().getReplyTo(), TEXT_PLAIN);
		if (mail.getBody().getLabelId() != null)
			entity.addTextBody("labelId", mail.getBody().getLabelId().toString(), TEXT_PLAIN);
		/**
		 * 是否使用模版发送
		 */
		if (mail.getContent().useTemplate()) {
			TemplateContent content = (TemplateContent) mail.getContent();
			entity.addTextBody("templateInvokeName", content.getTemplateInvokeName(), TEXT_PLAIN);
		} else {
			TextContent content = (TextContent) mail.getContent();
			if (content.getContent_type().equals(ScContentType.html)) {
				entity.addTextBody("html", content.getText(), TEXT_PLAIN);
			} else {
				entity.addTextBody("plain", content.getText(), TEXT_PLAIN);
			}
		}
		/**
		 * 是否使用地址列表
		 */
		if (mail.getTo() != null) {
			if (mail.getTo().useAddressList()) {
				entity.addTextBody("useAddressList", "true", TEXT_PLAIN);
				entity.addTextBody("to", mail.getTo().toString(), TEXT_PLAIN);
			} else {
				MailAddressReceiver receiver = (MailAddressReceiver) mail.getTo();

				if (!mail.getContent().useTemplate() && receiver.isBroadcastSend()) {
					entity.addTextBody("to", receiver.toString(), TEXT_PLAIN);
					if (StringUtils.isNotEmpty(receiver.getCcString()))
						entity.addTextBody("cc", receiver.getCcString(), TEXT_PLAIN);
					if (StringUtils.isNotEmpty(receiver.getBccString()))
						entity.addTextBody("bcc", receiver.getBccString(), TEXT_PLAIN);
				} else {
					if (mail.getBody().getXsmtpapi() == null || !mail.getBody().getXsmtpapi().containsKey("to")) {
						mail.getBody().addXsmtpapi("to", JSONArray.fromObject(receiver.getTo()));
					}
				}
			}
		}
		if (MapUtils.isNotEmpty(mail.getBody().getHeaders()))
			entity.addTextBody("headers", mail.getBody().getHeadersString(), TEXT_PLAIN);
		if (MapUtils.isNotEmpty(mail.getBody().getXsmtpapi()))
			entity.addTextBody("xsmtpapi", mail.getBody().getXsmtpapiString(), TEXT_PLAIN);
		entity.addTextBody("respEmailId", "true", TEXT_PLAIN);
		entity.addTextBody("useNotification", "false", TEXT_PLAIN);

		ContentType OCTEC_STREAM = ContentType.create("application/octet-stream", Charset.forName("UTF-8"));
		for (Object o : mail.getBody().getAttachments()) {
			if (o instanceof File) {
				entity.addBinaryBody("attachments", (File) o, OCTEC_STREAM, ((File) o).getName());
			} else if (o instanceof Attachment) {
				entity.addBinaryBody("attachments", ((Attachment) o).getContent(), OCTEC_STREAM,
						((Attachment) o).getName());
			} else {
				entity.addBinaryBody("attachments", (InputStream) o, OCTEC_STREAM, UUID.randomUUID().toString());
			}
		}
		httpPost.setEntity(entity.build());
		HttpResponse response = httpclient.execute(httpPost);
		ResponseData result = validate(response);
		httpPost.releaseConnection();
		httpclient.close();
		return result;
	}

	/**
	 * 发送短信
	 * 
	 * @param credential
	 * @param sms
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws SmsException
	 */
	public ResponseData sendSms(SendCloudSms sms) throws ClientProtocolException, IOException, SmsException {
		Asserts.notNull(sms, "sms");
		Asserts.notBlank(Config.sms_user, "sms_user");
		Asserts.notBlank(Config.sms_key, "sms_key");
		sms.validate();
		Credential credential = new Credential(Config.sms_user, Config.sms_key);
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("smsUser", credential.getApiUser());
		treeMap.put("msgType", sms.getMsgType().toString());
		treeMap.put("phone", sms.getPhoneString());
		treeMap.put("templateId", sms.getTemplateId().toString());
		treeMap.put("timestamp", String.valueOf((new Date()).getTime()));
		if (MapUtils.isNotEmpty(sms.getVars()))
			treeMap.put("vars", sms.getVarsString());
		String signature = Md5Util.md5Signature(treeMap, credential.getApiKey());
		treeMap.put("signature", signature);
		Iterator<String> iterator = treeMap.keySet().iterator();
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		while (iterator.hasNext()) {
			String key = iterator.next();
			params.add(new BasicNameValuePair(key, treeMap.get(key)));
		}

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(smsAPI);
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		ResponseData result = validate(response);
		httpPost.releaseConnection();
		httpclient.close();
		return result;
	}

	/**
	 * 发送语音
	 * 
	 * @param credential
	 * @param voice
	 * @return
	 * @throws VoiceException
	 * @throws ParseException
	 * @throws IOException
	 */
	public ResponseData sendVoice(SendCloudVoice voice) throws VoiceException, ParseException, IOException {
		Asserts.notNull(voice, "voice");
		Asserts.notBlank(Config.sms_user, "sms_user");
		Asserts.notBlank(Config.sms_key, "sms_key");
		voice.validate();
		Credential credential = new Credential(Config.sms_user, Config.sms_key);
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("smsUser", credential.getApiUser());
		treeMap.put("phone", voice.getPhone());
		treeMap.put("code", voice.getCode());
		treeMap.put("timestamp", String.valueOf((new Date()).getTime()));
		String signature = Md5Util.md5Signature(treeMap, credential.getApiKey());
		treeMap.put("signature", signature);
		Iterator<String> iterator = treeMap.keySet().iterator();
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		while (iterator.hasNext()) {
			String key = iterator.next();
			params.add(new BasicNameValuePair(key, treeMap.get(key)));
		}

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(voiceAPI);
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		ResponseData result = validate(response);
		httpPost.releaseConnection();
		httpclient.close();
		return result;
	}

	/**
	 * 解析返回结果
	 * 
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	private ResponseData validate(HttpResponse response) throws ParseException, IOException {
		String s = EntityUtils.toString(response.getEntity());
		ResponseData result = new ResponseData();
		if (JSONUtils.mayBeJSON(s)) {
			JSONObject json = JSONObject.fromObject(s);
			if (json.containsKey("statusCode")) {
				result.setStatusCode(json.getInt("statusCode"));
				result.setMessage(json.getString("message"));
				result.setResult(json.getBoolean("result"));
				result.setInfo(json.getJSONObject("info").toString());
			} else {
				result.setStatusCode(500);
				result.setMessage(json.toString());
			}
		} else {
			result.setStatusCode(response.getStatusLine().getStatusCode());
			result.setMessage("发送失败");
			result.setResult(false);
		}
		return result;
	}
}