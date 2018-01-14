package com.weiwensangsang.service.util.face;

/**
 * HTTP 请求的结果，包含返回的状态码和返回内容
 * result of HTTP request, includ status code and result content
 *
 * Created by Qi Wang on 2016/12/19.
 */
public class Response {

    private byte[] content;//返回的信息 result content
    private int status;//返回的状态码 status code
    private String message;

    public Response(){

    }

    public Response(byte[] content, int status){
        this.content = content;
        this.status = status;
        this.message = new String(content);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
