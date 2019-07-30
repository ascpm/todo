-- -----------------------------------------------------
-- Table `todo`
-- -----------------------------------------------------

-- -----------------------------------------------------
-- H2 DB
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todo` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '데이터아이디',
    `uid` VARCHAR(40) NOT NULL COMMENT '아이디',
    `name` VARCHAR(100) NOT NULL COMMENT '이름',
    `prev_id` BIGINT NULL COMMENT '이전데이터아이디',
    `next_id` BIGINT NULL COMMENT '다음데이터아이디',
    `done` BOOLEAN NOT NULL COMMENT '끝남여부',
    `created_time` DATETIME NOT NULL COMMENT '생성일시',
    `modified_time` DATETIME NULL COMMENT '수정일시',
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `todo` IS 'todo정보';
CREATE INDEX IF NOT EXISTS `IDX_UID` ON `jwt` (`uid`);
