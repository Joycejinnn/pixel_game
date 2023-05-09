CREATE DATABASE  IF NOT EXISTS  Java;
USE Java;

SET NAMES uft8mb4_bin;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE user(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(60) COLLATE utf8_bin NOT NULL,
    `password` varchar(60) COLLATE utf8_bin NOT NULL,
    `gender` varchar(60) COLLATE utf8_bin NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO user(`username`, `password`, `gender`) VALUES ('123', '123', 'female');

SET FOREIGN_KEY_CHECKS =1;


