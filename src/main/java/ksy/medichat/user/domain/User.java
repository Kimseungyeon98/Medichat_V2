package ksy.medichat.user.domain;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(nullable = false, length = 50, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    /*

    @Column(nullable = false)
    private String name;

    @Column
    private String auId;*/

    /*@Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(length = 5)
    private String zipcode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @ColumnDefault("2")
    private Integer role;

    @Lob
    private byte[] photo;

    private String photoTitle;

    @CreationTimestamp
    @Column(updatable = false)
    private Date registerDate;

    @Column
    private Date modifyDate;

    */





    /* implements UserDetails 에 관한 오버라이드*/
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}