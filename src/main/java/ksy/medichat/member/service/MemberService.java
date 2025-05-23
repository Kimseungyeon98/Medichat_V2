package ksy.medichat.member.service;

import ksy.medichat.member.domain.Member;
import ksy.medichat.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
