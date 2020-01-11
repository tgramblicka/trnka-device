SET FOREIGN_KEY_CHECKS=0; --

-- Dumping database structure for trnka-device
CREATE DATABASE IF NOT EXISTS `trnka-device` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `trnka-device`;

-- Dumping structure for table trnka-device.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(4) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Dumping structure for table trnka-device.brail_character
CREATE TABLE IF NOT EXISTS `brail_character` (
  `id` bigint(20) NOT NULL,
  `audio_file` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `brail_representation` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`brail_representation`)),
  `letter` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_brail_character_letter` (`letter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.sequence
CREATE TABLE IF NOT EXISTS `sequence` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` bigint(20) NOT NULL,
  `allowed_retries` int(11) DEFAULT NULL,
  `allowed_test_retries` int(2) DEFAULT NULL,
  `passing_rate_percentage` DOUBLE(2,2) DEFAULT NULL,
  `order` int(3) DEFAULT NULL,
  `audio_message` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `timeout` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.sequence_statistic
CREATE TABLE IF NOT EXISTS `sequence_statistic` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `sequence_id` bigint(20) DEFAULT NULL,
  `took` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1t3orv1oobdje4q8oy8u5uut0` (`user_id`),
  CONSTRAINT `FK1t3orv1oobdje4q8oy8u5uut0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.step
CREATE TABLE IF NOT EXISTS `step` (
  `id` bigint(20) NOT NULL,
  `preserve_order` bit(1) DEFAULT NULL,
  `brail_character_id` bigint(20) NOT NULL,
  `sequence_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf0ijab5k5ysid2xlfw1cai44e` (`brail_character_id`),
  KEY `FKhw20sfb0yro1o461pxnbqvepv` (`sequence_id`),
  CONSTRAINT `FKf0ijab5k5ysid2xlfw1cai44e` FOREIGN KEY (`brail_character_id`) REFERENCES `brail_character` (`id`),
  CONSTRAINT `FKhw20sfb0yro1o461pxnbqvepv` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.step_statistic
CREATE TABLE IF NOT EXISTS `step_statistic` (
  `id` bigint(20) NOT NULL,
  `retries` int(11) NOT NULL,
  `took` bigint(20) DEFAULT NULL,
  `step_id` bigint(20) NOT NULL,
  `sequence_statistic_id` bigint(20) DEFAULT NULL,
  `correct` bool DEFAULT NULL,
    PRIMARY KEY (`id`),
  KEY `FKj91gb64r3uqr35v5hx3lmhyu0` (`step_id`),
  KEY `FKfpbmmgdm2aqjhdcmgoj2bimbq` (`sequence_statistic_id`),
  CONSTRAINT `FKfpbmmgdm2aqjhdcmgoj2bimbq` FOREIGN KEY (`sequence_statistic_id`) REFERENCES `sequence_statistic` (`id`),
  CONSTRAINT `FKj91gb64r3uqr35v5hx3lmhyu0` FOREIGN KEY (`step_id`) REFERENCES `step` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `passed_methodics` (
    `sequence_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    KEY `FK_sequence_id` (`sequence_id`),
    KEY `FK_user_id` (`user_id`),
    CONSTRAINT `FK_sequence_id` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`),
    CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- Data exporting was unselected.


SET FOREIGN_KEY_CHECKS=1; --
