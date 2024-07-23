package library.md.controller;

import library.md.service.MemberService;
import library.md.view.MemberView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<MemberView> retrieve() {
        return memberService.retrieve();
    }

    @GetMapping("{id}")
    public MemberView retrieveById(@PathVariable Long id) {
        return memberService.retrieveById(id);
    }

    @PostMapping
    public MemberView create(@RequestBody MemberView memberView) {
        return memberService.create(memberView);
    }

    @PutMapping("{id}")
    public MemberView update(@PathVariable Long id, @RequestBody MemberView memberView) {
        return memberService.update(id, memberView);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }
}
