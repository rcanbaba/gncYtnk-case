package com.example.turkcell.service;

import com.example.turkcell.pojo.Book;

import java.util.List;

public interface SearchService {
    public List<Book> searchBook(String searchType, String searchParam);

}
