package library.md.controller;

import library.md.service.AuthorService;
import library.md.view.AuthorView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorView> retrieve() {
        return authorService.retrieve();
    }

    @GetMapping("{id}")
    public AuthorView retrieveById(@PathVariable Long id) {
        return authorService.retrieveById(id);
    }

    @PostMapping
    public AuthorView create(@RequestBody AuthorView authorView) {
        return authorService.create(authorView);
    }

    @PutMapping("{id}")
    public AuthorView update(@PathVariable Long id, @RequestBody AuthorView authorView) {
        return authorService.update(id, authorView);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
