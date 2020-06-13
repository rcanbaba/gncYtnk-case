package com.example.turkcell.pojo;

public class Book {

    private String name;
    private String writer;
    private String publisher;
    private String year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Book(String name, String writer, String publisher, String year) {
        this.name = name;
        this.writer = writer;
        this.publisher = publisher;
        this.year = year;
    }
}
