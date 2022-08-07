DROP TABLE IF EXISTS search_history;

CREATE TABLE search_history (
       PRIMARY KEY (search_history_id),
       search_history_id    INT             NOT NULL    AUTO_INCREMENT              COMMENT '검색기록 ID',
       keyword              VARCHAR(30)     NOT NULL    COMMENT '검색 키워드',
       type                 VARCHAR(30)     NOT NULL    COMMENT '검색 유형',
       search_member_id     INT             NULL        COMMENT '검색 회원 ID',
       create_date          TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP   COMMENT '검색일시'
);