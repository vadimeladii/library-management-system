package library.md.service;

import library.md.converter.BorrowRecordConverter;
import library.md.exception.InvalidRequestException;
import library.md.exception.NotFoundException;
import library.md.repository.BookRepository;
import library.md.repository.BorrowRecordRepository;
import library.md.repository.MemberRepository;
import library.md.repository.entity.BookEntity;
import library.md.repository.entity.BorrowRecordEntity;
import library.md.repository.entity.MemberEntity;
import library.md.utils.DateUtils;
import library.md.view.BorrowRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BorrowRecordConverter borrowRecordConverter;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public List<BorrowRecordView> retrieve() {
        return borrowRecordRepository.findAll()
                .stream()
                .map(borrowRecordConverter::convert)
                .toList();
    }

    public BorrowRecordView retrieveById(Long id) {
        return borrowRecordConverter.convert(borrowRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("BorrowRecord with ID %d not found", id))));
    }

    public BorrowRecordView create(BorrowRecordView view) {
        if (view.getId() != null) {
            throw new InvalidRequestException("Cannot create a new borrowRecord with an existing ID.");
        }

        BorrowRecordEntity entity = borrowRecordConverter.convert(view);
        return borrowRecordConverter.convert(borrowRecordRepository.save(entity));
    }

    public BorrowRecordView update(Long id, BorrowRecordView view) {
        if (!id.equals(view.getId())) {
            throw new InvalidRequestException(
                    String.format("The provided ID %d does not match the ID %d of the borrowRecord.", id, view.getId()));
        }

        BorrowRecordEntity entity = borrowRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("BorrowRecord with ID %d not found", id)));

        if (view.getMemberView() != null && view.getMemberView().getId() != null) {
            MemberEntity memberEntity = memberRepository.findById(view.getMemberView().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Member with ID %d not found", view.getMemberView().getId())));
            entity.setMemberEntity(memberEntity);
        }
        if (view.getBookView() != null && view.getBookView().getId() != null) {
            BookEntity bookEntity = bookRepository.findById(view.getBookView().getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Book with ID %d not found", view.getBookView().getId())));
            entity.setBookEntity(bookEntity);
        }

        entity.setBorrowDate(DateUtils.parseStringToLocalDate(view.getBorrowDate(), DateUtils.yyyy_MM_dd));
        entity.setReturnDate(DateUtils.parseStringToLocalDate(view.getReturnDate(), DateUtils.yyyy_MM_dd));

        return borrowRecordConverter.convert(entity);
    }

    public void delete(Long id) {
        BorrowRecordEntity entity = borrowRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("BorrowRecord with ID %d not found", id)));
        borrowRecordRepository.delete(entity);
    }
}
