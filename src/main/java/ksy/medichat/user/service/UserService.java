package ksy.medichat.user.service;

import ksy.medichat.user.dto.UserDTO;
import ksy.medichat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) {
        return UserDTO.toDTO(userRepository.save(UserDTO.toEntity(userDTO)));
    }
    public boolean checkId(String id) {
        return userRepository.existsById(id);
    }

}
