package com.lag.todoapp.todoapp.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QuoteDto {
    @JsonIgnore
    private Long id;

    private String quote;

    private String author;

    public QuoteDto() {
    }

    public QuoteDto(Long id,
                    String quote,
                    String author) {
        this.id = id;
        this.quote = quote;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "QuoteDto[" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ']';
    }
}
