package ru.ialexdm.springboot.WebLibrary2.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ialexdm.springboot.WebLibrary2.models.Book;
import ru.ialexdm.springboot.WebLibrary2.models.Reader;
import ru.ialexdm.springboot.WebLibrary2.services.ReadersService;
import ru.ialexdm.springboot.WebLibrary2.util.ReaderValidator;


import java.util.List;

@Controller
@RequestMapping("/readers")
public class ReadersController {
    private final ReadersService readersService;
    private final ReaderValidator readerValidator;

    public ReadersController(ReadersService readersService, ReaderValidator readerValidator) {
        this.readersService = readersService;
        this.readerValidator = readerValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("readers", readersService.findAll());
        return "readers/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model)
    {
        Reader reader = readersService.findOne(id);
        model.addAttribute("reader",reader);
        List<Book> readersBooks = readersService.findBooksByReaderId(id);
        model.addAttribute("readersBooks",readersBooks);
        model.addAttribute("isBooksDelayed", readersService.isBooksDelayed(readersBooks));

        return "readers/show";
    }
    @GetMapping("/{id}/edit")
    public String editReader(@PathVariable("id") Integer id, Model model){
        model.addAttribute("reader", readersService.findOne(id));
        return "readers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reader")@Valid Reader reader, BindingResult bindingResult, @PathVariable("id") Integer id){
        if (bindingResult.hasErrors()){
            return "readers/edit";
        }
        readersService.update(id, reader);
        return "redirect:/readers/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id){
        readersService.delete(id);
        return "redirect:/readers";
    }
    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader")Reader reader)
    {
        return "readers/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("reader")@Valid Reader reader, BindingResult bindingResult) {
        readerValidator.validate(reader,bindingResult);

        if (bindingResult.hasErrors()){
            return "readers/new";
        }
        readersService.save(reader);
        return "redirect:/readers";
    }
}
