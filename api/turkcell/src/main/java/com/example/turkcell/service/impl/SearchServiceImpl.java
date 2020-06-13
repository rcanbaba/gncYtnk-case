package com.example.turkcell.service.impl;

import com.example.turkcell.pojo.Book;
import com.example.turkcell.service.SearchService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {


    @Override
    public List<Book> searchBook(String searchType, String searchParam) {
      //  String csvFile = "../books.txt";   /// windows pc de path böyle
        /// macOs da path o şekilde çalışmıyor.
        /// String csvFile = "/Users/canbaba/Desktop/turkcell/books.txt";

        String currentDirectory = System.getProperty("user.dir"); // çözdü.
        String csvFile = currentDirectory + "/books.txt";
        BufferedReader br = null;
        String line = "";
        List<Book> bookList = new ArrayList<>();
        List<Book> finalList = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null )  {
                String[] books = line.split(";");
                Book book = new Book(books[0], books[1], books[2], books[3]);
                bookList.add(book);

            }

            finalList = getSearchResult(searchType, searchParam, bookList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return finalList;
    }

    private List<Book> getSearchResult(String searchType, String searchParam, List<Book> bookList) {

        List<Book> finalList = new ArrayList<>();

        if(searchType.equalsIgnoreCase("name") || searchType.equalsIgnoreCase("writer") ||
                searchType.equalsIgnoreCase("publisher") || searchType.equalsIgnoreCase("year") || searchType.equalsIgnoreCase("all")){
            for (Book tempBook : bookList) { // duplicate etmekten burada else if ler ile kaçındım.
                if((searchType.equalsIgnoreCase("name") || searchType.equalsIgnoreCase("all")) && tempBook.getName().toLowerCase().contains(searchParam))
                    finalList.add(tempBook);
                else if((searchType.equalsIgnoreCase("writer") || searchType.equalsIgnoreCase("all")) && tempBook.getWriter().toLowerCase().contains(searchParam))
                    finalList.add(tempBook);
                else if((searchType.equalsIgnoreCase("publisher") || searchType.equalsIgnoreCase("all"))&& tempBook.getPublisher().toLowerCase().contains(searchParam))
                    finalList.add(tempBook);
                else if((searchType.equalsIgnoreCase("year") || searchType.equalsIgnoreCase("all"))&& tempBook.getYear().contains(searchParam))
                    finalList.add(tempBook);
            }
        }
        return finalList;
    }
}
