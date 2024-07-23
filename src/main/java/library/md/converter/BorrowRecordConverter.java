package library.md.converter;

import library.md.repository.entity.BookEntity;
import library.md.repository.entity.BorrowRecordEntity;
import library.md.repository.entity.MemberEntity;
import library.md.utils.DateUtils;
import library.md.view.BorrowRecordView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BorrowRecordConverter implements Converter<BorrowRecordView, BorrowRecordEntity> {

    @Override
    public BorrowRecordEntity convert(BorrowRecordView view) {
        BorrowRecordEntity entity = new BorrowRecordEntity();

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(entity.getId());

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(entity.getId());

        entity.setId(view.getId());
        entity.setBookEntity(bookEntity);
        entity.setMemberEntity(memberEntity);
        entity.setBorrowDate(DateUtils.parseStringToLocalDate(view.getBorrowDate(), DateUtils.yyyy_MM_dd));
        entity.setReturnDate(DateUtils.parseStringToLocalDate(view.getReturnDate(), DateUtils.yyyy_MM_dd));

        return entity;
    }

    public BorrowRecordView convert(BorrowRecordEntity entity) {
        return null;
    }
}
