package library.md.converter;

import library.md.repository.entity.MemberEntity;
import library.md.utils.DateUtils;
import library.md.view.MemberView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements Converter<MemberView, MemberEntity> {

    @Override
    public MemberEntity convert(MemberView view) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(view.getId());
        memberEntity.setName(view.getName());
        memberEntity.setMembershipDate(DateUtils.parseStringToLocalDate(view.getMembershipDate(), DateUtils.yyyy_MM_dd));

        return memberEntity;
    }

    public MemberView convert(MemberEntity entity) {
        MemberView memberView = new MemberView();
        memberView.setId(entity.getId());
        memberView.setName(entity.getName());
        memberView.setEmail(entity.getEmail());
        memberView.setMembershipDate(entity.getMembershipDate() != null ? entity.getMembershipDate().toString() : null);

        return memberView;
    }
}
