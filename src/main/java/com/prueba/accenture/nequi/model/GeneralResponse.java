package com.prueba.accenture.nequi.model;

import lombok.Data;

@Data
public class GeneralResponse {
    private int code;
    private String message;
    private int status;
}
