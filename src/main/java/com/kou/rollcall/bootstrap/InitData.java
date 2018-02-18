package com.kou.rollcall.bootstrap;

import com.kou.rollcall.model.Author;
import com.kou.rollcall.model.Book;
import com.kou.rollcall.rest.AuthorRepository;
import com.kou.rollcall.rest.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent>
{
    public List<String> bookNames = new ArrayList<>(Arrays.asList("Spring 5", "Android Firebase", "Asp.Net MVC5",
            "MongoDB Tutorial", "Linux Bash", "MySQL Tutorial"
            , "Hibernate & Spring Boot", "Spring Data Rest", "Entity Framework Tutorial", "Robotics"));

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        Random rand = new Random();
        Author ramazan = authorRepository.getAuthorByName("Ramazan");
        Author resobyte = authorRepository.getAuthorByName("Resobyte");
        Author umut = authorRepository.getAuthorByName("Umut");

        List<Author> authorList = new ArrayList<>(Arrays.asList(ramazan, resobyte, umut));


        for (String bookName : bookNames)
        {
            Book book = new Book();
            book.setAuthor(authorList.get(rand.nextInt(3)));
            book.setBookName(bookName);
            bookRepository.save(book);
        }

        bookRepository.findAll().forEach(System.out::println);
    }
}
