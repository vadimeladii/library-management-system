package library.md.controller;

import library.md.service.BorrowRecordService;
import library.md.view.BorrowRecordView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("borrow-records")
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @GetMapping
    public List<BorrowRecordView> retrieve() {
        return borrowRecordService.retrieve();
    }

    @GetMapping("{id}")
    public BorrowRecordView retrieveById(@PathVariable Long id) {
        return borrowRecordService.retrieveById(id);
    }

    @PostMapping
    public BorrowRecordView create(@RequestBody BorrowRecordView view) {
        return borrowRecordService.create(view);
    }

    @PutMapping("{id}")
    public BorrowRecordView update(@PathVariable Long id, @RequestBody BorrowRecordView view) {
        return borrowRecordService.update(id, view);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        borrowRecordService.delete(id);
    }
}
