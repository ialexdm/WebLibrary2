package ru.ialexdm.springboot.WebLibrary2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Field should not be empty")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2,max = 128, message = "Field should be greater than 2 and less than 128 letters")
    private String author;
    @Min(value = 1800, message = "Year should be greater than 1800 and less than 2023")
    @Max(value = 2023, message = "Year should be greater than 0 and less than 128")
    @NotNull(message = "Field should not be empty")
    @Column(name = "year")
    private Integer year;
    @Column(name = "was_got")
    LocalDate wasGot;
    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private  Reader bookReader;

    public Book() {
    }

    public Book(Integer id, String name, String author, Integer year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public LocalDate getWasGot() {
        return wasGot;
    }

    public void setWasGot(LocalDate wasGot) {
        this.wasGot = wasGot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Reader getBookReader() {
        return bookReader;
    }

    public void setBookReader(Reader bookReader) {
        this.bookReader = bookReader;
    }
}
