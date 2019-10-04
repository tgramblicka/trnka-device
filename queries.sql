SELECT concat('DROP TABLE IF EXISTS `', table_name, '`;')
FROM information_schema.tables
WHERE table_schema = 'trnka-device';

SET FOREIGN_KEY_CHECKS=0; --
DROP TABLE IF EXISTS `test_result` CASCADE;
DROP TABLE IF EXISTS `user` CASCADE;
DROP TABLE IF EXISTS `step_statistic` CASCADE;
DROP TABLE IF EXISTS `step` CASCADE;
DROP TABLE IF EXISTS `sequence_statistic` CASCADE;
DROP TABLE IF EXISTS `learning_sequence` CASCADE;
DROP TABLE IF EXISTS `hibernate_sequence` CASCADE;
DROP TABLE IF EXISTS `brail_character` CASCADE;
DROP TABLE IF EXISTS `sequence` CASCADE;
DROP TABLE IF EXISTS `databasechangelog` CASCADE;
DROP TABLE IF EXISTS `databasechangeloglock` CASCADE;
SET FOREIGN_KEY_CHECKS=1;
