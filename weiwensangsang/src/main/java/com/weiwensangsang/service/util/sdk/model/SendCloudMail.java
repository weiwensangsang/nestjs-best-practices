package com.weiwensangsang.service.util.sdk.model;

import org.apache.http.util.Asserts;

/**
 * 邮件主体，包含邮件、收件人、邮件内容
 *
 * <P>
 * <OL>
 * <LI><B>body</B> 邮件，必须
 * <LI><B>content</B> 邮件内容，必须
 * <LI><B>to</B> 收件人，非必须(可以在xsmtpapi中添加)
 * </OL>
 * <P>
 * 
 * @author Administrator
 *
 */
public class SendCloudMail {
	public MailBody body;
	public Receiver to;
	public Content content;

	public MailBody getBody() {
		return body;
	}

	public void setBody(MailBody body) {
		this.body = body;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Receiver getTo() {
		return to;
	}

	public void setTo(Receiver to) {
		this.to = to;
	}

	public void validate() throws Throwable {
		Asserts.notNull(body, "body");
		body.validate();
		Asserts.notNull(content, "content");
		content.validate();
		if (to != null)
			to.validate();
	}
}