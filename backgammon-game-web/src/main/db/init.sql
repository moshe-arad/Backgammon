CREATE SCHEMA IF NOT EXISTS `backgammon`;
USE `backgammon`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `game_users`;

CREATE TABLE `game_users`
(
	`user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `user_name` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    `last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
    PRIMARY KEY(`user_id`)
);

INSERT INTO `game_users` (`first_name`, `last_name`, `email`, `user_name`, `password`, `role`, `last_updated_date`, `last_updated_by`, `created_date`, `created_by`) VALUES ('Moshe', 'Arad', 'moshe.arad.mailbox@gmail.com', 'moshe.arad', '1234', 'ROLE_USER', NOW(), 0, NOW(), 0);
