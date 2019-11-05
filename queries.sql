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
DROP TABLE IF EXISTS `DATABASECHANGELOG` CASCADE;
DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK` CASCADE;
SET FOREIGN_KEY_CHECKS=1;


create table hibernate_sequences(
    sequence_name VARCHAR NOT NULL,
    next_val INTEGER NOT NULL
)




    DELETE FROM databasechangelog WHERE id like '001-insert-data.sql'


alter table step drop foreign key FKf0ijab5k5ysid2xlfw1cai44e


	INSERT INTO hibernate_sequence (next_val) VALUES (1);
	INSERT INTO hibernate_sequence (next_val) VALUES (2);
	INSERT INTO hibernate_sequence (next_val) VALUES (3);
	INSERT INTO hibernate_sequence (next_val) VALUES (4);
	INSERT INTO hibernate_sequence (next_val) VALUES (4);

SELECT * FROM brail_character ORDER BY letter asc



SET FOREIGN_KEY_CHECKS=0; --
TRUNCATE brail_character;
TRUNCATE hibernate_sequence;
TRUNCATE USER;
TRUNCATE sequence;
TRUNCATE step;
TRUNCATE sequence_statistic;
TRUNCATE step_statistic;
SET FOREIGN_KEY_CHECKS=1;




