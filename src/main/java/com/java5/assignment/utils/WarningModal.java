package com.java5.assignment.utils;

import lombok.Getter;

@Getter
public class WarningModal extends Modal{
    public String link;
    public String text;
    public WarningModal(String message,String text, String link) {
        super.setTitle("Warning");
        super.setMessage(message);
        this.text = text;
        this.link = link;
    }
}
