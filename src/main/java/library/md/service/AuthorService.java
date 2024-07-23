package library.md.service;

import library.md.converter.AuthorConverter;
import library.md.exception.InvalidRequestException;
import library.md.exception.NotFoundException;
import library.md.repository.AuthorRepository;
import library.md.repository.entity.AuthorEntity;
import library.md.utils.DateUtils;
import library.md.view.AuthorView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    public List<AuthorView> retrieve() {
        return authorRepository.findAll()
                .stream()
                .map(authorConverter::convert)
                .toList();
    }

    public AuthorView retrieveById(Long id) {
        return authorConverter.convert(authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author with ID %d not found", id))));
    }

    public AuthorView create(AuthorView authorView) {
        if (authorView.getId() != null) {
            throw new InvalidRequestException("Cannot create a new author with an existing ID.");
        }

        AuthorEntity entity = authorConverter.convert(authorView);
        return authorConverter.convert(authorRepository.save(entity));
    }

    public AuthorView update(Long id, AuthorView authorView) {
        if (!id.equals(authorView.getId())) {
            throw new InvalidRequestException(
                    String.format("The provided ID %d does not match the ID %d of the author.", id, authorView.getId()));
        }

        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author with ID %d not found", id)));
        authorEntity.setName(authorView.getName());
        authorEntity.setBiography(authorView.getBiography());
        authorEntity.setDateOfBirth(DateUtils.parseStringToLocalDate(authorView.getDateOfBirth(), DateUtils.yyyy_MM_dd));

        return authorConverter.convert(authorRepository.save(authorEntity));
    }

    public void delete(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author with ID %d not found", id)));

        authorRepository.delete(authorEntity);
    }
}
