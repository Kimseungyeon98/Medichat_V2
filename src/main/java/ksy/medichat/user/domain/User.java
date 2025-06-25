package ksy.medichat.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(nullable = false, length = 50, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

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
}