package com.weiwensangsang.service.util.sdk.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String CHARSET = "utf-8";
    public static String server = "http://www.sendcloud.net";
    // 普通邮件发送
    public static String send_api = "http://api.sendcloud.net/apiv2/mail/send";
    // 地址列表发送
    public static String send_template_api = "http://api.sendcloud.net/apiv2/mail/sendtemplate";
    // 短信发送
    public static String send_sms_api = "http://www.sendcloud.net/smsapi/send";
    // 语音发送
    public static String send_voice_api = "http://www.sendcloud.net/smsapi/sendVoice";
    // 邮件user
    public static String api_user = null;
    // 邮件key
    public static String api_key = null;
    // 短信user
    public static String sms_user = "weiwensangsang";
    // 短信key
    public static String sms_key = "HsNpDCYUW7Le0i8LIsMqGXm9WgahmmbJ";
    // 最大收件人数
    public static final int MAX_RECEIVERS = 100;
    // 最大地址列表数
    public static final int MAX_MAILLIST = 5;
    // 邮件内容大小
    public static final int MAX_CONTENT_SIZE = 1024 * 1024;

}