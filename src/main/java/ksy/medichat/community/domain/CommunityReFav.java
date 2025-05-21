package ksy.medichat.community.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cboard_re_fav")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityReFav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long creNum;

    private Long memNum;
}