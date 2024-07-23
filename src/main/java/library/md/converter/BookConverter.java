package library.md.converter;

import library.md.exception.NotFoundException;
import library.md.repository.AuthorRepository;
import library.md.repository.entity.AuthorEntity;
import library.md.repository.entity.BookEntity;
import library.md.repository.entity.Status;
import library.md.utils.DateUtils;
import library.md.view.AuthorView;
import library.md.view.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter implements Converter<BookView, BookEntity> {

    private final AuthorRepository authorRepository;

    @Override
    public BookEntity convert(BookView view) {
        BookEntity entity = new BookEntity();
        entity.setId(view.getId());
        entity.setTitle(view.getTitle());
        entity.setIsbn(view.getIsbn());
        entity.setPublishedDate(DateUtils.parseStringToLocalDate(view.getPublishedDate(), DateUtils.yyyy_MM_dd));
        entity.setStatus(view.getStatus() != null ? Status.valueOf(view.getStatus()) : null);

        if (view.getAuthor() != null && view.getAuthor().getId() != null) {
            AuthorEntity authorEntity = authorRepository.findById(view.getAuthor().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Author with ID %d not found", view.getAuthor().getId())));
            entity.setAuthorEntity(authorEntity);
        }

        return entity;
    }

    public BookView convert(BookEntity entity) {
        BookView view = new BookView();
        view.setId(entity.getId());
        view.setTitle(entity.getTitle());
        view.setIsbn(entity.getIsbn());
        view.setPublishedDate(entity.getPublishedDate() != null ? entity.getPublishedDate().toString() : null);
        view.setStatus(entity.getStatus().toString());
        if (entity.getAuthorEntity() != null) {
            AuthorView authorView = new AuthorView();
            authorView.setId(entity.getAuthorEntity().getId());
            view.setAuthor(authorView);
        }

        return view;
    }
}
