INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01011111111', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '김병주', '19980101', 'M');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01022222222', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '김현수', '19980101', 'M');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01033333333', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '도경윤', '19960101', 'M');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01044444444', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '송호찬', '19960101', 'M');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01055555555', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '이의인', '19980101', 'F');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01066666666', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '조만기', '19950101', 'M');

INSERT INTO member (phone_num, password, nickname, birthday, gender)
VALUES ('01077777777', '$2a$10$K1D/vs2n6gB3E5ugaCOVQexuVDiHckyR1q/UneXoSEZfBm/z3ZwVS', '황신지', '19960101', 'F');

INSERT INTO laundry_like (member_id, laundry_id) VALUES (1, 1);

INSERT INTO laundry_visit_history (laundry_id, facility_id, member_id) VALUES (1, 1, 1);
