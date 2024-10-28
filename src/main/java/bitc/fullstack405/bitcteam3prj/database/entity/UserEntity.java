package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // 유저키 pk, board 테이블과 fk

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String userPw;

  @Column(nullable = false)
  private String email;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime joinedAt;// 유저 가입일(생성일)

  @Column(nullable = false)
  private char gender; // 성별

  @Column(nullable = false)
  private int age; // 나이

  private String movieCate; // 선호 영화장르

  private String profileImageName;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<BoardEntity> boardList;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<BoardLikeEntity> boardLikeList;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<MovieLikeEntity> movieLikeList;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<MovieBoardRatingEntity> movieRatingList;


  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<BoardCommentEntity> boardCommentList;

  @Column(nullable = false)
  private char deletedYn = 'N';

  @OneToOne(mappedBy="user")
  @ToString.Exclude
  private ManagerEntity manager;
}
