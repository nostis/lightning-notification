package com.nostis.lightning_core.util;

import lombok.Data;

@Data
public class EmailInformations extends Informations {
    public EmailInformations(String to, String from, String subject){
        this.to = to;
        this.from = from;
        this.subject = subject;
    }

    private String to;
    private String from;
    private String subject;
}
