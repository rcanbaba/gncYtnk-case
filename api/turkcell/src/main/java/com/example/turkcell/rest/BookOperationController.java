package com.example.turkcell.rest;

import com.example.turkcell.boundary.ApiResponse;
import com.example.turkcell.pojo.Book;
import com.example.turkcell.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bookOperation")
@CrossOrigin("*")
public class BookOperationController {


    @Autowired
    SearchService searchService;


    @GetMapping(path = "/search/{searchType}/{searchParam}")
    public ResponseEntity<ApiResponse> getCreatedIntents(@PathVariable String searchType, @PathVariable String searchParam) {

        try {
           List<Book> bookList = searchService.searchBook(searchType,searchParam.toLowerCase());
            return new ResponseEntity<>(new ApiResponse("Arama Sonuçları",bookList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
