CREATE SCHEMA IF NOT EXISTS `backgammon_test`;
USE `backgammon_test`;

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
