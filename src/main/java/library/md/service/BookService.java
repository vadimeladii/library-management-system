package library.md.service;

import library.md.converter.BookConverter;
import library.md.exception.InvalidRequestException;
import library.md.exception.NotFoundException;
import library.md.exception.ReferencedEntityException;
import library.md.repository.AuthorRepository;
import library.md.repository.BookRepository;
import library.md.repository.BorrowRecordRepository;
import library.md.repository.entity.BookEntity;
import library.md.repository.entity.Status;
import library.md.utils.DateUtils;
import library.md.view.BookView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final AuthorRepository authorRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public List<BookView> retrieve() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::convert)
                .toList();
    }

    public BookView retrieveById(Long id) {
        return bookConverter.convert(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found", id))));
    }

    public BookView create(BookView bookView) {
        if (bookView.getId() != null) {
            throw new InvalidRequestException("Cannot create a new book with an existing ID.");
        }

        BookEntity entity = bookConverter.convert(bookView);
        return bookConverter.convert(bookRepository.save(entity));
    }

    public BookView update(Long id, BookView bookView) {
        if (!id.equals(bookView.getId())) {
            throw new InvalidRequestException(
                    String.format("The provided ID %d does not match the ID %d of the book.", id, bookView.getId()));
        }

        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found", id)));
        entity.setTitle(bookView.getTitle());
        entity.setIsbn(bookView.getIsbn());
        entity.setPublishedDate(DateUtils.parseStringToLocalDate(bookView.getPublishedDate(), DateUtils.yyyy_MM_dd));
        entity.setStatus(Status.valueOf(bookView.getStatus()));

        if (bookView.getAuthor() != null && bookView.getAuthor().getId() != null) {
            entity.setAuthorEntity(authorRepository.findById(bookView.getAuthor().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Author with ID %d not found", bookView.getAuthor().getId()))));
        }

        return bookConverter.convert(bookRepository.save(entity));
    }

    public void delete(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found", id)));

        List<Long> linkedIds = borrowRecordRepository.findIdsByBookEntityId(id);

        Optional<String> linkedIdsString = linkedIds.isEmpty()
                ? Optional.empty()
                : Optional.of(linkedIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));

        linkedIdsString.ifPresentOrElse(
                ids -> {
                    throw new ReferencedEntityException(
                            String.format("Cannot perform the operation due to a reference with BorrowEntity. Linked IDs: %s", ids));
                },
                () -> bookRepository.delete(bookEntity)
        );
    }
}
