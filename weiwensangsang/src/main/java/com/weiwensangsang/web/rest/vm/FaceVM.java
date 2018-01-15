package com.weiwensangsang.web.rest.vm;

/**
 * Created by xiazhen on 2018/1/15.
 */
public class FaceVM {

    private String content;

    private String type;

    private String state;

    private String control;

    private String name;

    private String outerId;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    @Override
    public String toString() {
        return "FaceVM{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", control='" + control + '\'' +
                ", name='" + name + '\'' +
                ", outerId='" + outerId + '\'' +
                '}';
    }
}
