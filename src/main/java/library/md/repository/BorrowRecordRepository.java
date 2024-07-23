package library.md.repository;

import library.md.repository.entity.BorrowRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecordEntity, Long> {
}
