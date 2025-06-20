package ksy.medichat.user.service;

import ksy.medichat.user.domain.User;
import ksy.medichat.user.dto.UserDTO;
import ksy.medichat.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public UserDTO saveMember(UserDTO userDTO) {
        User user = UserDTO.toEntity(userDTO);
        return UserDTO.toDTO(memberRepository.save(user));
    }
    public boolean checkId(String id) {
        return memberRepository.existsById(id);
    }
}
