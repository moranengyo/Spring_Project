package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "board")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // 게시글 id, pk, user 테이블 FK

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private int visitCnt; // 조회수

    @Column(nullable = false)
    private String category; // 카테고리

    @Column(nullable = true)
    private String warning; // 주의


    @ManyToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private UserEntity user;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BoardCommentEntity> commentList;

    @ToString.Exclude
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    List<BoardLikeEntity> boardLikeList;
}
