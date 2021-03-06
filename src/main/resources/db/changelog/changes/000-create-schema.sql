SET FOREIGN_KEY_CHECKS=0; --

-- Dumping database structure for trnka-device
-- CREATE DATABASE `trnka-device2` CHARACTER SET = 'utf8' COLLATE = 'utf8_bin';
-- SET character_set_server = 'utf8';
-- USE `trnka-device`;


-- Device sequence
create sequence `device_seq` start with 200 minvalue 200 maxvalue 9223372036854775806 increment by 1 cache 20 nocycle;

-- Dumping structure for table trnka-device.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL,
  `external_id` bigint(20),
  `username` varchar(255) DEFAULT NULL,
  `code` varchar(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_code` (`code`)
) ;


-- Dumping structure for table trnka-device.brail_character
CREATE TABLE IF NOT EXISTS `brail_character` (
  `id` bigint(20) NOT NULL,
  `audio_file` varchar(255)  DEFAULT NULL,
  `brail_representation` varchar(255)  DEFAULT NULL,
  `letter` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_brail_representation` (`brail_representation`)
);


-- Dumping structure for table trnka-device.sequence
CREATE TABLE IF NOT EXISTS `sequence` (
  `dtype` varchar(31)  NOT NULL,
  `id` bigint(20) NOT NULL,
  `external_id` bigint(20),
  `allowed_retries` int(11) DEFAULT NULL,
  `allowed_test_retries` int(2) DEFAULT NULL,
  `passing_rate_percentage` DECIMAL(4,2) DEFAULT NULL,
  `level` int(3) DEFAULT NULL,
  `audio_message` varchar(255)  DEFAULT NULL,
  `timeout` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.sequence_statistic
CREATE TABLE IF NOT EXISTS `sequence_statistic` (
  `id` bigint(20) NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `sequence_id` bigint(20) DEFAULT NULL,
  `took` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK1t3orv1oobdje4q8oy8u5uut0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ;

-- Data exporting was unselected.

-- Dumping structure for table trnka-device.step
CREATE TABLE IF NOT EXISTS `step` (
  `id` bigint(20) NOT NULL,
  `external_id` bigint(20),
  `preserve_order` BOOL DEFAULT NULL,
  `brail_character_id` bigint(20) NOT NULL,
  `sequence_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKf0ijab5k5ysid2xlfw1cai44e` FOREIGN KEY (`brail_character_id`) REFERENCES `brail_character` (`id`),
  CONSTRAINT `FKhw20sfb0yro1o461pxnbqvepv` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`)
);

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
  CONSTRAINT `FKfpbmmgdm2aqjhdcmgoj2bimbq` FOREIGN KEY (`sequence_statistic_id`) REFERENCES `sequence_statistic` (`id`),
  CONSTRAINT `FKj91gb64r3uqr35v5hx3lmhyu0` FOREIGN KEY (`step_id`) REFERENCES `step` (`id`)
);

CREATE TABLE IF NOT EXISTS `passed_methodics` (
    `sequence_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    CONSTRAINT `FK_sequence_id_passed_methodics` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`),
    CONSTRAINT `FK_user_id_passed_methodics` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `user_sequences` (
    `sequence_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    CONSTRAINT `FK_sequence_id_user_sequences` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`),
    CONSTRAINT `FK_user_id_user_sequences` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `synchronization` (
    `id` bigint(20) NOT NULL,
    `executed_on` datetime NOT NULL,
    `type` varchar(255) NOT NULL,
    `status` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);


-- Data exporting was unselected.


SET FOREIGN_KEY_CHECKS=1; --
