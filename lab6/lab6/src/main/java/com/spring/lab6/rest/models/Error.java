package com.spring.lab6.rest.models;

import org.springframework.http.HttpStatus;

public class Error {
    HttpStatus code;
    String msg;

    public Error(HttpStatus code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpStatus getCode() {
        return code;
    }
    public String getMsg() { return msg; }
}
