package ksy.medichat.user.dto;

import ksy.medichat.user.domain.User;
import ksy.medichat.user.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record LoginUser(User user) implements UserDetails {

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public UserRole getUserRole() {return user.getRole();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    // 기타 메서드 구현
}
