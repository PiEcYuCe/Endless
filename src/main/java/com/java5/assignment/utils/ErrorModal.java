package com.java5.assignment.utils;

public class ErrorModal extends Modal {
    public ErrorModal(String message) {
        super.setTitle("Error");
        super.setMessage(message);
    }
}
