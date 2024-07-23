package library.md.view;

import lombok.Data;

@Data
public class BookView {

    private Long id;
    private String title;
    private String isbn;
    private String publishedDate;
    private String status;
    private AuthorView author;
}
