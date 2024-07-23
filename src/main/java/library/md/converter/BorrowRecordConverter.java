package library.md.converter;

import library.md.exception.NotFoundException;
import library.md.repository.BookRepository;
import library.md.repository.MemberRepository;
import library.md.repository.entity.BookEntity;
import library.md.repository.entity.BorrowRecordEntity;
import library.md.repository.entity.MemberEntity;
import library.md.utils.DateUtils;
import library.md.view.BookView;
import library.md.view.BorrowRecordView;
import library.md.view.MemberView;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BorrowRecordConverter implements Converter<BorrowRecordView, BorrowRecordEntity> {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    public BorrowRecordEntity convert(BorrowRecordView view) {
        BorrowRecordEntity entity = new BorrowRecordEntity();
        entity.setId(view.getId());

        if (view.getBookView() != null && view.getBookView().getId() != null) {
            BookEntity bookEntity = bookRepository.findById(view.getBookView().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found", view.getBookView().getId())));
            entity.setBookEntity(bookEntity);
        }
        if (view.getMemberView() != null && view.getMemberView().getId() != null) {
            MemberEntity memberEntity = memberRepository.findById(view.getMemberView().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Member with ID %d not found", view.getMemberView().getId())));
            entity.setMemberEntity(memberEntity);
        }

        entity.setBorrowDate(DateUtils.parseStringToLocalDate(view.getBorrowDate(), DateUtils.yyyy_MM_dd));
        entity.setReturnDate(DateUtils.parseStringToLocalDate(view.getReturnDate(), DateUtils.yyyy_MM_dd));

        return entity;
    }

    public BorrowRecordView convert(BorrowRecordEntity entity) {
        BorrowRecordView view = new BorrowRecordView();
        view.setId(entity.getId());

        if (entity.getBookEntity() != null && entity.getBookEntity().getId() != null) {
            BookView bookView = new BookView();
            bookView.setId(entity.getBookEntity().getId());
            view.setBookView(bookView);
        }
        if (entity.getMemberEntity() != null && entity.getMemberEntity().getId() != null) {
            MemberView memberView = new MemberView();
            memberView.setId(entity.getMemberEntity().getId());
            view.setMemberView(memberView);
        }

        view.setBorrowDate(entity.getBorrowDate() != null ? entity.getBorrowDate().toString() : null);
        view.setReturnDate(entity.getReturnDate() != null ? entity.getReturnDate().toString() : null);

        return view;
    }
}
