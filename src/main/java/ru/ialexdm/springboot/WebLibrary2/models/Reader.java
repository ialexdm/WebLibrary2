package ru.ialexdm.springboot.WebLibrary2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "full_name")
    @NotEmpty(message = "Field should not be empty")
    @Size(min = 2,max = 128, message = "Field should be greater than 2 and less than 128 letters")
    private String fullName;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0 and less than 128")
    @Max(value = 128, message = "Age should be greater than 0 and less than 128")
    @NotNull(message = "Field should not be empty")
    private Integer age;

    @OneToMany(mappedBy = "bookReader", cascade = CascadeType.PERSIST)
    private List<Book> readableBooks;
    public Reader(){
    }

    public Reader(Integer id, String fullName, Integer age) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getReadableBooks() {
        return readableBooks;
    }
    public void setReadableBooks(List<Book> readableBooks) {
        this.readableBooks = readableBooks;
    }
    public void addBook(Book book){
        if (this.readableBooks == null) {
            this.readableBooks = new ArrayList<>();
        }
        this.readableBooks.add(book);
    }
}
