package com.kou.rollcall.rest;

import com.kou.rollcall.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>, CrudRepository<Author, Long>
{
    Author getAuthorByName(String name);
}
