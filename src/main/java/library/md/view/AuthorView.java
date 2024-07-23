package library.md.view;

import lombok.Data;

@Data
public class AuthorView {

    private Long id;
    private String name;
    private String dateOfBirth;
    private String biography;
}
