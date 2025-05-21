package ksy.medichat.community.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cboard_fav")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityFav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cboNum;

    private Long memNum;
}