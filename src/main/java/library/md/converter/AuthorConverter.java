package library.md.converter;

import library.md.repository.entity.AuthorEntity;
import library.md.utils.DateUtils;
import library.md.view.AuthorView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<AuthorView, AuthorEntity> {

    @Override
    public AuthorEntity convert(AuthorView view) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(view.getId());
        authorEntity.setName(view.getName());
        authorEntity.setBiography(view.getBiography());
        authorEntity.setDateOfBirth(
                DateUtils.parseStringToLocalDate(view.getDateOfBirth(), DateUtils.yyyy_MM_dd));
        return authorEntity;
    }

    public AuthorView convert(AuthorEntity entity) {
        AuthorView authorView = new AuthorView();
        authorView.setId(entity.getId());
        authorView.setName(entity.getName());
        authorView.setBiography(entity.getBiography());
        authorView.setDateOfBirth(entity.getDateOfBirth() != null ? entity.getDateOfBirth().toString() : null);
        return authorView;
    }
}
