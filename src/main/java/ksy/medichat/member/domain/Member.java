package ksy.medichat.member.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString(exclude = {"memPhoto"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_num")
    private Long memNum;

    @Column(name = "mem_id", nullable = false, length = 50, unique = true)
    private String memId;

    @Column(name = "mem_name", nullable = false)
    private String memName;

    @Column(name = "mem_auth")
    private int memAuth;

    @Lob
    @Column(name = "mem_photo")
    private byte[] memPhoto;

    @Column(name = "mem_photo_name")
    private String memPhotoName;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private MemberDetail memberDetail;
}