package bitc.fullstack405.bitcteam3prj.database.constant;

import lombok.Getter;

public enum MovieCategory {
    CATE_1("드라마"),
    CATE_2("공포"),
    CATE_3("미스터리"),
    CATE_4("SF"),
    CATE_5("인물"),
    CATE_6("액션");

    @Getter private final String description;

    MovieCategory(String description) {
        this.description = description;
    }
}
