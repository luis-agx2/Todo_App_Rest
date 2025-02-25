package com.lag.todoapp.todoapp.model.response;

import com.lag.todoapp.todoapp.client.dto.QuoteDto;

public class LoginDto {
    private String jwt;

    private QuoteDto quote;

    public LoginDto() {
    }

    public LoginDto(String jwt,
                    QuoteDto quote) {
        this.jwt = jwt;
        this.quote = quote;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public QuoteDto getQuote() {
        return quote;
    }

    public void setQuote(QuoteDto quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "LoginResponse[" +
                "jwt='" + jwt + '\'' +
                "quote='" + quote + '\'' +
                ']';
    }
}
