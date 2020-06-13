package com.example.turkcell;

import com.example.turkcell.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SpringBootTest
public class CreateBookDBText {

    @Test
    public void createBookTextFile(){

        String csvFile = "../books.csv";
        BufferedReader br = null;
        String line = "";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            List<Book> bookList = new ArrayList<>();
            line = br.readLine(); // to do not read first line
            while ((line = br.readLine()) != null) {
                String[] books = line.split(",");
                Book book = new Book(books[1], books[2],books[11],books[10]);
                bookList.add(book);
            }

            writeBooksToText2(bookList);

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
    }



    public void writeBooksToText2(List<Book> bookList){

        File file = new File("./books.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for (Book book : bookList){
                br.write(book.getName() + ";" + book.getWriter() + ";" + book.getPublisher() + ";" + book.getYear() + System.getProperty("line.separator"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
