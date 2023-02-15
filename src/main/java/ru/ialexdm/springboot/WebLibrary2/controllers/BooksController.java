package ru.ialexdm.springboot.WebLibrary2.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ialexdm.springboot.WebLibrary2.models.Book;
import ru.ialexdm.springboot.WebLibrary2.models.Reader;
import ru.ialexdm.springboot.WebLibrary2.services.BooksService;
import ru.ialexdm.springboot.WebLibrary2.services.ReadersService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final ReadersService readersService;

    public BooksController(BooksService booksService, ReadersService readersService) {
        this.booksService = booksService;
        this.readersService = readersService;
    }
    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                        @RequestParam(value = "limit", defaultValue = "3") @Min(1) int limit,
                        @RequestParam(value = "sort_by_year", defaultValue = "true") boolean sortByYear) {
        List<Book> books;
        List<Integer> totalPages;
        if (limit == 0){
            books = booksService.findAll(sortByYear);
            totalPages = Collections.emptyList();
        }
        else {
            BooksService.PagedBook pagingBooks = booksService.findAll(page, limit, sortByYear);
            books = pagingBooks.getBooksOnPage();
            totalPages = pagingBooks.getPageNumbers();
        }
        model.addAttribute("books", books);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumber", page);
        model.addAttribute("limit", limit);
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, @ModelAttribute("reader")Reader reader){
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        Reader bookReader = booksService.getBookReader(book);
        if (bookReader != null)
        {
            model.addAttribute("bookReader", bookReader);
        }
        else {
            model.addAttribute("readers", readersService.findAll());
        }
        return  "books/show";
    }
    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult, @PathVariable("id") Integer id){
        if (bindingResult.hasErrors())
        {
            return "books/edit";
        }

        booksService.update(id, book);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/give")
    public String transfer(@ModelAttribute("book")Book book,
                       @ModelAttribute("reader") Reader reader,
                       @PathVariable("id") Integer id){
        booksService.transfer(id, reader);
        return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/take")
    public String transfer(@ModelAttribute("book")Book book,
                           @PathVariable("id") Integer id){
        booksService.transfer(id, null);
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id)
    {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/find")
    public String find(@RequestParam(value = "name", required = false) String name, Model model){
        List<Book> foundBooks= booksService.findAllByName(name);
        model.addAttribute("books",foundBooks);
        return "books/find";
    }

}
