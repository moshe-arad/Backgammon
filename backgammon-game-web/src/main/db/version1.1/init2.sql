DROP SCHEMA IF EXISTS `backgammon_2`;
CREATE SCHEMA `backgammon_2`;
USE `backgammon_2`;

DROP TABLE IF EXISTS `user_in_game_room`;
DROP TABLE IF EXISTS `game_users`;
DROP TABLE IF EXISTS `game_rooms`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

DROP TABLE IF EXISTS `group_authorities`;
DROP TABLE IF EXISTS `group_members`;
DROP TABLE IF EXISTS `groups`;




/********** from spring docs **********/

create table users(
	`u_id` BIGINT NOT NULL AUTO_INCREMENT primary key,
	`username` varchar(50) not null primary key,
	`password` varchar(50) not null,
	`enabled` boolean not null,
	`last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL
);

create table authorities (
	`auth_id` BIGINT NOT NULL AUTO_INCREMENT primary key,
	`username` varchar(50) not null,
	`authority` varchar(50) not null,
	`last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
	constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table groups (
	id bigint NOT NULL AUTO_INCREMENT primary key,
	group_name varchar(50) not null,
	`last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL
);

create table group_authorities (
	group_id bigint not null,
	authority varchar(50) not null,
	`last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
	constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
	id bigint NOT NULL AUTO_INCREMENT primary key,
	username varchar(50) not null,
	group_id bigint not null,
	`last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
	constraint fk_group_members_group foreign key(group_id) references groups(id)
);

/********** my tables **********/

CREATE TABLE `game_users`
(
	`user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `role` VARCHAR(255) NOT NULL,
    `last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
    `u_id` BIGINT NOT NULL,
    PRIMARY KEY(`user_id`),
    FOREIGN KEY (`u_id`) REFERENCES `users`(`u_id`)
);

DROP TABLE IF EXISTS `game_rooms`;

CREATE TABLE `game_rooms`
(
	`game_room_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `private` BIT(1) NOT NULL,
    `white` BIGINT DEFAULT NULL,
    `black` BIGINT DEFAULT NULL,
    `opened_by` BIGINT NOT NULL,
    `speed` BIT(3) NOT NULL,
    `last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
    `token` VARCHAR(255) AS (SHA1(CONCAT(`name`, "This is a qwe secret 123", `created_by`, `created_date`))),
    PRIMARY KEY(`game_room_id`)
);


DROP TABLE IF EXISTS `user_in_game_room`;

CREATE TABLE `user_in_game_room`
(
	`user_id` BIGINT NOT NULL,
	`game_room_id` BIGINT NOT NULL,
    `last_updated_date` DATETIME NOT NULL,
    `last_updated_by` BIGINT NOT NULL,
    `created_date` DATETIME NOT NULL,
    `created_by` BIGINT NOT NULL,
    PRIMARY KEY(`user_id`, `game_room_id`),
    FOREIGN KEY (`user_id`) REFERENCES `game_users`(`user_id`),
    FOREIGN KEY (`game_room_id`) REFERENCES `game_rooms`(`game_room_id`)
);








