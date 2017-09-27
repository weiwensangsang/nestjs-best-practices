package com.weiwensangsang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ResponseMessage{

    private static final ResponseMessage INSTANCE = new ResponseMessage();

    private ResponseMessage() {

    }

    public static ResponseMessage message(String message) {
        INSTANCE.setMessage(message);
        return INSTANCE;
    }

    @Size(max = 50)
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ResponseMessage set(String Message) {
        this.Message = Message;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
            ", Message='" + Message + '\'' +
            "}";
    }
}
