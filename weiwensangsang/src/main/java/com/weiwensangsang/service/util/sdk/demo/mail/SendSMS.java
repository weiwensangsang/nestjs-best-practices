package com.weiwensangsang.service.util.sdk.demo.mail;

import com.weiwensangsang.service.util.sdk.builder.SendCloudBuilder;
import com.weiwensangsang.service.util.sdk.core.SendCloud;
import com.weiwensangsang.service.util.sdk.exception.SmsException;
import com.weiwensangsang.service.util.sdk.model.SendCloudSms;
import com.weiwensangsang.service.util.sdk.util.ResponseData;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

public class SendSMS {

    public static void send() throws ClientProtocolException, IOException, SmsException {
        SendCloudSms sms = new SendCloudSms();
        sms.setMsgType(0);
        sms.setTemplateId(11145);
        sms.addPhone("12345678911,12345678910");
        sms.addVars("company", "爱发信");
        sms.addVars("date", "2016.04.02");

        SendCloud sc = SendCloudBuilder.build();
        ResponseData res = sc.sendSms(sms);

        System.out.println(res.getResult());
        System.out.println(res.getStatusCode());
        System.out.println(res.getMessage());
        System.out.println(res.getInfo());
    }

    public static void sendActivated(String phone, String code) throws ClientProtocolException, IOException, SmsException {
        SendCloudSms sms = new SendCloudSms();
        sms.setMsgType(0);
        sms.setTemplateId(11161);
        sms.addPhone(phone);
        sms.addVars("code", code);
        SendCloud sc = SendCloudBuilder.build();
        ResponseData res = sc.sendSms(sms);
    }

}
