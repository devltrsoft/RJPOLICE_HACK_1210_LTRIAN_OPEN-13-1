package com.ltrsoft.rajashtanuserapplication.classes;

public class Message {
    public static String Sent_by_me="me";

    public static String Sent_by_Bot="bot";
    String message;
    String sendBy;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public Message() {
    }

    public Message(String message, String sendBy) {
        this.message = message;
        this.sendBy = sendBy;
    }
}
