package com.quoteBoard.entity;

public class Quote {
    private Long id;
    private String quote;
    private String writer;

    public Quote(String quote, String writer) {
        this.quote = quote;
        this.writer = writer;
    }

    public Quote(Long id, String quote, String writer) {
        this.id = id;
        this.quote = quote;
        this.writer = writer;
    }

    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public String getWriter() {
        return writer;
    }

    public void update(String quote, String writer) {
        this.quote = quote;
        this.writer = writer;
    }

}
