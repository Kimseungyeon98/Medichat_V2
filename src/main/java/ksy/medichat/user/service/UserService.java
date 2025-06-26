package ksy.medichat.user.service;

import ksy.medichat.user.domain.User;
import ksy.medichat.user.dto.LoginUser;
import ksy.medichat.user.dto.UserDTO;
import ksy.medichat.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        return new LoginUser(user);
    }

    public UserDTO save(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        if(userDTO.getRole()==null){
            userDTO.setRole("USER");
        }
        userRepository.save(UserDTO.toEntity(userDTO));
        return userDTO;
    }
    public boolean checkId(String id) {
        return userRepository.existsById(id);
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        return UserDTO.toDTO(user);
    }
}
