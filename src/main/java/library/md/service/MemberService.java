package library.md.service;

import library.md.converter.MemberConverter;
import library.md.exception.InvalidRequestException;
import library.md.exception.NotFoundException;
import library.md.repository.MemberRepository;
import library.md.repository.entity.MemberEntity;
import library.md.utils.DateUtils;
import library.md.view.MemberView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    public List<MemberView> retrieve() {
        return memberRepository.findAll()
                .stream()
                .map(memberConverter::convert)
                .toList();
    }

    public MemberView retrieveById(Long id) {
        return memberConverter.convert(memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with ID %d not found", id))));
    }

    public MemberView create(MemberView memberView) {
        if (memberView.getId() != null) {
            throw new InvalidRequestException("Cannot create a new member with an existing ID.");
        }

        MemberEntity entity = memberConverter.convert(memberView);
        return memberConverter.convert(memberRepository.save(entity));
    }

    public MemberView update(Long id, MemberView memberView) {
        if (!id.equals(memberView.getId())) {
            throw new InvalidRequestException(
                    String.format("The provided ID %d does not match the ID %d of the member.", id, memberView.getId()));
        }
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with ID %d not found", id)));
        memberEntity.setId(memberView.getId());
        memberEntity.setName(memberView.getName());
        memberEntity.setEmail(memberView.getEmail());
        memberEntity.setMembershipDate(DateUtils.parseStringToLocalDate(memberView.getMembershipDate(), DateUtils.yyyy_MM_dd));

        return memberConverter.convert(memberRepository.save(memberEntity));
    }

    public void delete(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Member with ID %d not found", id)));

        memberRepository.delete(memberEntity);
    }
}
