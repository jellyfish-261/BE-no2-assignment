USE schedule;

CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '스케쥴 식별자',
    task VARCHAR(255) NOT NULL COMMENT '할일',
    author_name VARCHAR(100) COMMENT '작성자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_at DATETIME COMMENT '작성일',
    updated_at DATETIME COMMENT '수정일'
);

CREATE TABLE author
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    name VARCHAR(100) NOT NULL COMMENT '작성자',
    email VARCHAR(255) COMMENT '이메일',
    created_at DATETIME COMMENT '작성일',
    updated_at DATETIME COMMENT '수정일'
);

CREATE TABLE schedule2
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '스케쥴 식별자',
    task VARCHAR(255) NOT NULL COMMENT '할일',
    author_id BIGINT NOT NULL COMMENT '작성자 식별자',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_at DATETIME COMMENT '작성일',
    updated_at DATETIME COMMENT '수정일',
    FOREIGN KEY (author_id) REFERENCES author(id)
);
