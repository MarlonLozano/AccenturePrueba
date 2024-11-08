package com.prueba.accenture.nequi.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GlobalException extends Exception {

    private String code;
    private String message;
    private String action;
    private String messageId;
    private Integer codeHttp;


    public GlobalException(String code, String message, String action, String messageId, Integer codeHttp) {
        this.code = code;
        this.message = message;
        this.action = action;
        this.messageId = messageId;
        this.codeHttp = codeHttp;
    }
}
