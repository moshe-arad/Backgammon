DROP SCHEMA IF EXISTS `backgammon_test_2`;
CREATE SCHEMA `backgammon_test_2`;
USE `backgammon_test_2`;

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
	`username` varchar(50) not null primary key,
	`password` varchar(50) not null,
	`enabled` boolean not null,
	`last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System"
);

create table authorities (
	`username` varchar(50) not null,
	`authority` varchar(50) not null,
	`last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
	constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table groups (
	id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	group_name varchar(50) not null,
	`last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System"
);

create table group_authorities (
	group_id bigint not null,
	authority varchar(50) not null,
	`last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
	constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
	id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username varchar(50) not null,
	group_id bigint not null,
	`last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
	constraint fk_group_members_group foreign key(group_id) references groups(id)
);

/********** my tables **********/

CREATE TABLE `game_users`
(
	`user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
    `username` varchar(50) not null,
    PRIMARY KEY(`user_id`),
    FOREIGN KEY (`username`) REFERENCES `users`(`username`) 
);

DROP TABLE IF EXISTS `game_rooms`;

CREATE TABLE `game_rooms`
(
	`game_room_id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `private` TINYINT NOT NULL,
    `white` BIGINT DEFAULT NULL,
    `black` BIGINT DEFAULT NULL,
    `opened_by` BIGINT NOT NULL,
    `speed` TINYINT NOT NULL,
    `last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
    `group_id` BIGINT NOT NULL,
    PRIMARY KEY(`game_room_id`),
    constraint fk_game_rooms_group foreign key(group_id) references groups(id)
);


DROP TABLE IF EXISTS `user_in_game_room`;

CREATE TABLE `user_in_game_room`
(
	`user_id` BIGINT NOT NULL,
	`game_room_id` BIGINT NOT NULL,
    `last_modified_date` DATETIME NOT NULL default now(),
    `last_modified_by` varchar(50) NOT NULL default "System",
    `created_date` DATETIME NOT NULL default now(),
    `created_by` varchar(50) NOT NULL default "System",
    PRIMARY KEY(`user_id`, `game_room_id`),
    FOREIGN KEY (`user_id`) REFERENCES `game_users`(`user_id`),
    FOREIGN KEY (`game_room_id`) REFERENCES `game_rooms`(`game_room_id`)
);








