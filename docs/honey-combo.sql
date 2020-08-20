CREATE DATABASE `honeycombo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `honeycombo`;

-- user file, roles start
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ubirth` date DEFAULT NULL,
  `uemail` varchar(100) NOT NULL,
  `uid` varchar(100) NOT NULL,
  `unick` varchar(100) NOT NULL,
  `upw` varchar(100) NOT NULL,
  `uregdate` datetime DEFAULT NULL,
  `usex` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k36a9qh1930ig59gueuhwo3gd` (`uemail`),
  UNIQUE KEY `UK_niqq2ridnntviivkixh7kco0r` (`unick`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `profiles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- user file, roles end


-- feed file, hashtag, like start
CREATE TABLE `feed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `editdate` datetime DEFAULT NULL,
  `regdate` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `feed_uid_fk_idx` (`uid`),
  CONSTRAINT `feed_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `files` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fid` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72a93fsvixnlw9fbivi6to6es` (`fid`),
  CONSTRAINT `FK72a93fsvixnlw9fbivi6to6es` FOREIGN KEY (`fid`) REFERENCES `feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hashtag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `feed_hashtag` (
  `fid` int NOT NULL,
  `hid` int NOT NULL,
  PRIMARY KEY (`fid`,`hid`),
  KEY `feed_hashtag_hid_fk_idx` (`hid`),
  CONSTRAINT `feed_hashtag_fid_fk` FOREIGN KEY (`fid`) REFERENCES `feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `feed_hashtag_hid_fk` FOREIGN KEY (`hid`) REFERENCES `hashtag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `feed_like` (
  `fid` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`fid`,`uid`),
  KEY `feed_like_uid_fk_idx` (`uid`),
  CONSTRAINT `feed_like_fid_fk` FOREIGN KEY (`fid`) REFERENCES `feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `feed_like_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- feed file, hashtag, like end


-- relationship start
CREATE TABLE `relationship` (
  `relationuid` int NOT NULL,
  `uid` int NOT NULL,
  `is_following` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`relationuid`,`uid`),
  KEY `relationship_uid_fk_idx` (`uid`),
  CONSTRAINT `relationship_rid_fk` FOREIGN KEY (`relationuid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relationship_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- relationship end


-- comment start
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `depth` int DEFAULT NULL,
  `editdate` datetime DEFAULT NULL,
  `fid` int DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `regdate` datetime DEFAULT NULL,
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_uid_fk_idx` (`uid`),
  KEY `comment_fid_fk_idx` (`fid`),
  CONSTRAINT `comment_fid_fk` FOREIGN KEY (`fid`) REFERENCES `feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment_like` (
  `cid` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`cid`,`uid`),
  KEY `comment_like_uid_fk_idx` (`uid`),
  CONSTRAINT `comment_like_cid_fk` FOREIGN KEY (`cid`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_like_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- comment end


-- notification start
CREATE TABLE `notification_non_read` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL,
  `fid` int DEFAULT NULL,
  `lid` int NOT NULL,
  `regdate` datetime DEFAULT NULL,
  `rid` int NOT NULL,
  `state` int NOT NULL,
  `uid` int NOT NULL,
  `action_uid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_non_read_uid_fk_idx` (`uid`),
  KEY `notification_non_read_action_uid_fk_idx` (`action_uid`),
  CONSTRAINT `notification_non_read_action_uid_fk` FOREIGN KEY (`action_uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `notification_non_read_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notification_read` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL,
  `expiredate` datetime DEFAULT NULL,
  `fid` int NOT NULL,
  `lid` int NOT NULL,
  `rid` int NOT NULL,
  `state` int NOT NULL,
  `uid` int NOT NULL,
  `action_uid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_read_uid_fk_idx` (`uid`),
  KEY `notification_read_action_uid_fk_idx` (`action_uid`),
  CONSTRAINT `notification_read_action_uid_fk` FOREIGN KEY (`action_uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `notification_read_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- notification end


-- contest start
CREATE TABLE `contest` (
  `id` int NOT NULL AUTO_INCREMENT,
  `regdate` datetime DEFAULT NULL,
  `round` int NOT NULL,
  `theme` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contest_feed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `like_count` int NOT NULL,
  `regdate` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contest_feed_uid_fk_idx` (`uid`),
  KEY `contest_feed_cid_fk_idx` (`cid`),
  CONSTRAINT `contest_feed_cid_fk` FOREIGN KEY (`cid`) REFERENCES `contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contest_feed_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contest_feed_files` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cfid` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnpiatdt0orlfe7mus0n66p04l` (`cfid`),
  CONSTRAINT `FKnpiatdt0orlfe7mus0n66p04l` FOREIGN KEY (`cfid`) REFERENCES `contest_feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contest_feed_like` (
  `cfid` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`cfid`,`uid`),
  KEY `contest_feed_like_uid_fk_idx` (`uid`),
  CONSTRAINT `contest_feed_like_cfid_fk` FOREIGN KEY (`cfid`) REFERENCES `contest_feed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contest_feed_like_uid_fk` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- contest end


-- recommand start
CREATE TABLE `recommand` (
  `age_group` int NOT NULL,
  `gender` int NOT NULL,
  `hashtag` int NOT NULL,
  `accumulate` int DEFAULT NULL,
  `avg` int DEFAULT NULL,
  `cnt` int DEFAULT NULL,
  `today` int DEFAULT NULL,
  PRIMARY KEY (`age_group`,`gender`,`hashtag`),
  KEY `recommand_hid_fk_idx` (`hashtag`),
  CONSTRAINT `recommand_hid_fk` FOREIGN KEY (`hashtag`) REFERENCES `hashtag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- recommand end