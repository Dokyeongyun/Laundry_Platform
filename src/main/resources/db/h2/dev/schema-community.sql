DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS category;

CREATE TABLE post (
        PRIMARY KEY (post_id),
        post_id     INT          NOT NULL AUTO_INCREMENT,
        category_id INT          NOT NULL,
        writer      INT          NOT NULL,
        title       VARCHAR(50)  NOT NULL,
        contents    VARCHAR(300) NOT NULL,
        create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
        like_num    INT          NOT NULL DEFAULT 0,
        update_date DATETIME
);

CREATE TABLE comment (
        PRIMARY KEY (comment_id),
        comment_id   INT          NOT NULL AUTO_INCREMENT,
        post_id      INT          NOT NULL,
        writer       INT          NOT NULL,
        contents     VARCHAR(300) NOT NULL,
        create_date  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
        like_num     INT          NOT NULL DEFAULT 0,
        update_date  DATETIME
);

CREATE TABLE reply (
        PRIMARY KEY (reply_id),
        reply_id     INT          NOT NULL AUTO_INCREMENT,
        comment_id   INT          NOT NULL,
        writer       INT          NOT NULL,
        contents     VARCHAR(300) NOT NULL,
        create_date  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
        like_num     INT          NOT NULL DEFAULT 0,
        update_date  DATETIME
);

CREATE TABLE review (
        PRIMARY KEY (review_id),
        review_id    INT            NOT NULL AUTO_INCREMENT,
        laundry_id   INT            NOT NULL,
        writer_id    INT            NOT NULL,
        contents     VARCHAR(300)   NOT NULL,
        rating       INT            NOT NULL DEFAULT 1,
        visit_date   DATE,
        create_date  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
        update_date  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE category (
        PRIMARY KEY (category_id),
        category_id     INT         NOT NULL AUTO_INCREMENT,
        category_name   VARCHAR(10)
);