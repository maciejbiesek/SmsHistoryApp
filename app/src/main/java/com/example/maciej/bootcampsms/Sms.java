package com.example.maciej.bootcampsms;

import java.io.Serializable;

public class Sms implements Serializable {
    private String body;
    private String number;
    private String date;
    private String type;

    public Sms(String body, String number, String date, String type) {
        this.body = body;
        this.number = number;
        this.date = date;
        this.type = type;
    }

    public String getBody() { return body; }
    public String getNumber() { return number; }
    public String getDate() { return date; }
    public String getType() { return type; }

}
