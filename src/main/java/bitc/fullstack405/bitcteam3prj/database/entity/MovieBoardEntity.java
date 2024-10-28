package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "movie_board")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MovieBoardEntity extends BaseEntity{

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // 영화정보 id, pk, manager 테이블과 fk

  @OneToOne
  @JoinColumn(name = "movie_id", nullable = false)
  private MovieEntity movie; // 영화 id, movie 테이블과 fk

  @ManyToOne
  @JoinColumn(name="manager_id")
  @ToString.Exclude
  private ManagerEntity manager; // 관리자 id, manager 테이블과 fk

  @ToString.Exclude
  @OneToMany(mappedBy = "movieBoard", cascade = CascadeType.ALL)
  private List<MovieBoardRatingEntity> movieRatingList;

  @Column(nullable = false)
  @ColumnDefault("0")
  private int viewCnt; // 조회수

}
