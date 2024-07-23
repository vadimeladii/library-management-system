package library.md.controller;

import library.md.service.BookService;
import library.md.view.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookView> retrieve() {
        return bookService.retrieve();
    }

    @GetMapping("{id}")
    public BookView retrieveById(@PathVariable Long id) {
        return bookService.retrieveById(id);
    }

    @PostMapping
    public BookView create(@RequestBody BookView bookView) {
        return bookService.create(bookView);
    }

    @PutMapping("{id}")
    public BookView update(@PathVariable Long id, @RequestBody BookView bookView) {
        return bookService.update(id, bookView);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
