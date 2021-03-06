SET FOREIGN_KEY_CHECKS=0; --

insert into USER (id, username, code) values (1, 'test1', 'aaaa');

INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (1, NULL, '[1]', 'a');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (2, NULL, '[1,2]', 'b');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (3, NULL, '[1,4]', 'c');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (4, NULL, '[1,4,5]', 'd');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (5, NULL, '[1,5]', 'e');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (6, NULL, '[1,2,4]', 'f');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (7, NULL, '[1,2,4,5]', 'g');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (8, NULL, '[1,2,5]', 'h');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (9, NULL, '[2,4]', 'i');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (10, NULL, '[2,4,5]', 'j');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (11, NULL, '[3,4]', 'k');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (12, NULL, '[4,5,6]', 'l');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (13, NULL, '[1,3,4]', 'm');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (14, NULL, '[1,3,4,5]', 'n');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (15, NULL, '[1,3,5]', 'o');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (16, NULL, '[1,2,3,4]', 'p');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (17, NULL, '[1,2,3,4,5]', 'q');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (18, NULL, '[1,2,3,5]', 'r');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (19, NULL, '[2,3,4]', 's');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (20, NULL, '[2,3,4,5]', 't');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (21, NULL, '[1,3,6]', 'u');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (22, NULL, '[1,2,3,6]', 'v');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (23, NULL, '[1,3,4,6]', 'x');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (24, NULL, '[1,3,4,5,6]', 'y');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (25, NULL, '[1,3,5,6]', 'z');




INSERT INTO `sequence` (`dtype`, `id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('LS', 101, 1, 'ONE', 100000);
INSERT INTO `sequence` (`dtype`, `id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('LS', 106, 1, 'TWO', 100000);

INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (102, b'0', 1, 101);
INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (103, b'0', 2, 101);
INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (104, b'0', 12, 101);
INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (105, b'0', 5, 101);
INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (107, b'0', 11, 106);
INSERT INTO `step` (`id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (108, b'0', 21, 106);

INSERT INTO hibernate_sequence (next_val) VALUES (106);
INSERT INTO hibernate_sequence (next_val) VALUES (107);
INSERT INTO hibernate_sequence (next_val) VALUES (108);
INSERT INTO hibernate_sequence (next_val) VALUES (109);
INSERT INTO hibernate_sequence (next_val) VALUES (110);




SET FOREIGN_KEY_CHECKS=1; --
