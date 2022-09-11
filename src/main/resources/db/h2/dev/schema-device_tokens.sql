DROP TABLE IF EXISTS device_tokens;

CREATE TABLE device_tokens(
        PRIMARY KEY(member_id),
        member_id        INT NOT NULL,
        token            VARCHAR NOT NULL,
        device_type      VARCHAR NOT NULL,
        created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
