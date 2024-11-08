package com.prueba.accenture.nequi.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequestException extends GlobalException {


    public BadRequestException(String code, String message, String action, String messageId, Integer codeHttp) {
        super(code, message, action, messageId, codeHttp);
    }
}
