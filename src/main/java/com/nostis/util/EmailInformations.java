package com.nostis.util;

import lombok.Data;

@Data
public class EmailInformations extends Informations {
    public EmailInformations(String to, String subject, String content){
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    private String to;
    private String subject;
    private String content;
}
