package ksy.medichat.user.dto;

import jakarta.validation.constraints.Pattern;
import ksy.medichat.user.domain.User;
import lombok.*;

@Getter
@Setter
//@ToString(exclude = {"photo"})
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO{

    private Long code;
    @Pattern(regexp = "^[A-Za-z0-9-]{4,}$")
    private String id;
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String password;
    private String role;
    /*@NotBlank
    private String name;
    private Integer role;
    private byte[] photo;
    private String photoTitle;
    private String auId;
    @NotEmpty
    private String birth;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @Size(min = 5, max = 5)
    private String zipcode;
    @NotBlank
    private String address;
    @NotEmpty
    private String addressDetail;
    private Date registerDate;
    private Date modifyDate;*/


    // Entity (User + UserDetail) → DTO 변환
    public static UserDTO toDTO(User user) {
        return UserDTO.builder().code(user.getCode()).id(user.getId()).password(user.getPassword()).role(user.getRole()).build();
    }

    // DTO → Entity (User + UserDetail) 변환
    public static User toEntity(UserDTO dto) {
        return User.builder().code(dto.getCode()).id(dto.getId()).password(dto.getPassword()).role(dto.getRole()).build();
    }

}
