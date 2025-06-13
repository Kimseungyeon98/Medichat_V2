package ksy.medichat.member.domain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
@Getter
@Setter
@ToString(exclude = {"photo","memberDetail"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(nullable = false, length = 50, unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @ColumnDefault("2")
    private Integer role;

    @Lob
    private byte[] photo;

    private String photoTitle;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private MemberDetail memberDetail;
}