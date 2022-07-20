DROP TABLE IF EXISTS laundry;
DROP TABLE IF EXISTS facility;
DROP TABLE IF EXISTS facility_holding;

CREATE TABLE laundry (
       PRIMARY KEY (laundry_id),
       laundry_id            INT                NOT NULL    AUTO_INCREMENT          COMMENT '빨래방ID',
       name                  VARCHAR(30)        NOT NULL    COMMENT '빨래방이름',
       jibun_address         VARCHAR(100)       NULL        COMMENT '지번주소',
       jibun_address_detail  VARCHAR(100)       NULL        COMMENT '지번주소상세',
       doro_address          VARCHAR(100)       NULL        COMMENT '도로명주소',
       doro_address_detail   VARCHAR(100)       NULL        COMMENT '도로명주소상세',
       latitude              DECIMAL(13, 10)    NULL        COMMENT '위도',
       longitude             DECIMAL(13, 10)    NULL        COMMENT '경도',
       partnership           BOOLEAN            NOT NULL    DEFAULT false           COMMENT '제휴여부'
);


CREATE TABLE facility (
       PRIMARY KEY (facility_id),
       facility_id  INT         AUTO_INCREMENT  COMMENT '시설ID',
       name         VARCHAR(30) NOT NULL        COMMENT '시설명',
       price        INT         DEFAULT 0       COMMENT '이용가격'
);

CREATE TABLE facility_holding (
       laundry_id   INT NOT NULL    COMMENT '빨래방ID',
       facility_id  INT NOT NULL    COMMENT '시설ID'
);

