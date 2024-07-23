package library.md.repository;

import library.md.repository.entity.BorrowRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecordEntity, Long> {

    @Query("SELECT b.id FROM BorrowRecordEntity b WHERE b.bookEntity.id = :bookId")
    List<Long> findIdsByBookEntityId(@Param("bookId") Long bookId);

    @Query("SELECT b.id FROM BorrowRecordEntity b WHERE b.memberEntity.id = :memberId")
    List<Long> findIdsByMemberEntityId(@Param("memberId") Long memberId);
}
