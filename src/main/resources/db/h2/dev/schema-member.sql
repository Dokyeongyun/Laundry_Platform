DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS laundry_visit_history;
DROP TABLE IF EXISTS laundry_like;

CREATE TABLE member (
       PRIMARY KEY (member_id),
       member_id        INT         NOT NULL AUTO_INCREMENT,
       phone_num        CHAR(11)    NOT NULL UNIQUE,
       password         VARCHAR(100) NOT NULL,
       nickname         VARCHAR(10) NOT NULL,
       birthday         DATE,
       gender           CHAR(1),
       auto_login_yn    BOOLEAN     NOT NULL DEFAULT false,
       join_date        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
       withdrawal_date  TIMESTAMP
);

CREATE TABLE laundry_visit_history(
        PRIMARY KEY (visit_history_seq),
        visit_history_seq INT         NOT NULL AUTO_INCREMENT,
        laundry_id        INT         NOT NULL,
        facility_id       INT         NOT NULL,
        member_id         INT         NOT NULL,
        visit_date        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE laundry_like(
        PRIMARY KEY(laundry_like_id),
        laundry_like_id   INT         NOT NULL AUTO_INCREMENT,
        member_id         INT         NOT NULL,
        laundry_id        INT         NOT NULL,
        like_date         TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);