package com.nostis.util;

import lombok.Data;

@Data
public class EmailInformations extends Informations {
    public EmailInformations(String to, String subject){
        this.to = to;
        this.subject = subject;
    }

    private String to;
    private String subject;
}
