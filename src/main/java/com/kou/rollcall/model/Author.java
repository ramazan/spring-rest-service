package com.kou.rollcall.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String publishHome;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
//    private Set<Book> bookList = new HashSet<>();

    public Author()
    {
    }

    public Author(String name, String description, String publishHome)
    {
        this.name = name;
        this.description = description;
        this.publishHome = publishHome;
    }
}
