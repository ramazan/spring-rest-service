package com.kou.rollcall.rest;

import com.kou.rollcall.model.Author;
import com.kou.rollcall.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BookService
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @PostMapping(value = "/saveBook")
    private ResponseEntity<Book> saveBook(@RequestBody Book book)
    {
        bookRepository.save(book);
        log.info("Books Saved  : " + book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping(value = "/getBooks")
    private List<Book> getBooks()
    {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/getAuthors")
    private List<Author> getAuthors()
    {
        return authorRepository.findAll();
    }

    @GetMapping(value = {"/getBooks/{authorName}", "/getAuthors/{authorName}"})
    private List<Book> getBooks(@PathVariable("authorName") String author)
    {
        return bookRepository.getAllByAuthor(authorRepository.getAuthorByName(author));
    }

    @GetMapping(value = "/getBooks/{authorName}/{bookName}")
    private Book getBooks(@PathVariable("authorName") String author, @PathVariable(value = "bookName") String name)
    {
        return bookRepository.getBookByAuthorAndBookName(authorRepository.getAuthorByName(author), name);
    }

}
