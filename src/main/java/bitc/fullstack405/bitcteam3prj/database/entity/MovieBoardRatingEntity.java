package bitc.fullstack405.bitcteam3prj.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "movie_board_rating")
public class MovieBoardRatingEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "movie_board_id")
    @ToString.Exclude
    private MovieBoardEntity movieBoard;


    @ManyToOne
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "movie_rating")
    private int movieRating;


}
