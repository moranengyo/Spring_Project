package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
@Data
@Entity
@Table(name = "manager")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ManagerEntity {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // 매니저 id

  @OneToOne
  @JoinColumn(name="user_id")
  @ToString.Exclude
  private UserEntity user; // 매니저 유저id


  @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<MovieBoardEntity> movieArticleList;
}
