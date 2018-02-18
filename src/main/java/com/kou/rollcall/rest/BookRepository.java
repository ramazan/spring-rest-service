package com.kou.rollcall.rest;

import com.kou.rollcall.model.Author;
import com.kou.rollcall.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>
{

    List<Book> getAllByAuthor(Author author);

    Book getBookByAuthorAndBookName(Author author, String bookName);

}
