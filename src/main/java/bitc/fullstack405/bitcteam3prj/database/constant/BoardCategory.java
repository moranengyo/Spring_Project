package bitc.fullstack405.bitcteam3prj.database.constant;

import lombok.Getter;

public enum BoardCategory {
    CATE_1("공지"),
    CATE_2("리뷰"),
    CATE_3("자유");

    @Getter
    private final String description;

    BoardCategory(String description) {
        this.description = description;
    }
}
