package library.md.view;

import lombok.Data;

@Data
public class BorrowRecordView {

    private Long id;
    private MemberView memberView;
    private BookView bookView;
    private String borrowDate;
    private String returnDate;
}
