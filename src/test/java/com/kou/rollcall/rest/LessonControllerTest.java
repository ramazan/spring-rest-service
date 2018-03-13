package com.kou.rollcall.rest;

import com.kou.rollcall.KouApplication;
import com.kou.rollcall.bootstrap.InitData;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Log4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KouApplication.class)
public class LessonControllerTest
{

    @Autowired
    private InitData initData;
//
//    @Test
//    public void testGetBooksByAuthor()
//    {
//        Author author = authorRepository.getAuthorByName("Ramazan");
//
//        for (Book tmpBooks : bookRepository.getAllByAuthor(author))
//        {
//            Assert.assertEquals(tmpBooks.getAuthor().getName(), "Ramazan");
//        }
//    }
//
//    @Test
//    public void testGetInitialBooks()
//    {
//        List<Book> books = bookRepository.findAll();
//        Assert.assertTrue(books.size() > 0);
//    }
//
//
//    @Test
//    public void testGetBooksByAuthorId()
//    {
//        List<Book> books = bookRepository.findBookByAuthorId(1L);
//        books.forEach(System.out::println);
//        Assert.assertTrue(books.size() > 0);
//
//        books = bookRepository.findBookByAuthorName("Ramazan");
//        books.forEach(System.out::println);
//        Assert.assertTrue(books.size() > 0);
//    }
//
//    @Test
//    public void testSaveBook()
//    {
//        Author author = authorRepository.getAuthorByName("Ramazan");
//
//        Book book = new Book();
//        book.setBookName("Junit 5 Steps");
//        book.setAuthor(author);
//        bookRepository.save(book);
//
//        Book savedBook = bookRepository.getBookByAuthorAndBookName(author, book.getBookName());
//
//        Assert.assertEquals(book.getBookName(), savedBook.getBookName());
//        Assert.assertEquals(book.getAuthor().getName(), savedBook.getAuthor().getName());
//        Assert.assertNotEquals(null, savedBook.getId());
//    }
}