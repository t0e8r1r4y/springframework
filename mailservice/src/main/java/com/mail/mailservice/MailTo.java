package com.mail.mailservice;

import lombok.Data;

@Data
public class MailTo {
    private String address;
    private String title;
    private String message;
}
