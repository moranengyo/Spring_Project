package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.lang.annotation.Documented;
import java.util.List;

@Data
@Entity
@Table(name = "director")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DirectorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id; // 감독 id, pk, movie 테이블에 fk

  @Column(nullable = false)
  private String name;

  @ToString.Exclude
  @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
  private List<MovieEntity> movieList;
}
