SET FOREIGN_KEY_CHECKS=0; --

insert into user (id, external_id, username, code) values (1, 1, 'test1', 'aaaa');

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
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (11, NULL, '[1,3]', 'k');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (12, NULL, '[1,2,3]', 'l');
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
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (23, NULL, '[2,4,5,6]', 'w');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (24, NULL, '[1,3,4,6]', 'x');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (25, NULL, '[1,3,4,5,6]', 'y');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (26, NULL, '[1,3,5,6]', 'z');
-- special characters"
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (27, NULL, '[1,6]', 'á');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (28, NULL, '[4]', 'ä');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (29, NULL, '[1,4,6]', '?');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (30, NULL, '[1,4,5,6]', 'ď');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (31, NULL, '[3,4,5]', 'é');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (32, NULL, '[3,4]', 'í');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (33, NULL, '[4,6]', 'ľ');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (34, NULL, '[4,5,6]', 'ľ'); -- makke l
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (35, NULL, '[1,2,4,6]', 'ň');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (36, NULL, '[2,4,6]', 'ó');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (37, NULL, '[2,3,4,5,6]', 'ô');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (38, NULL, '[1,2,3,5,6]', 'ŕ');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (39, NULL, '[1,5,6]', 'š');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (40, NULL, '[1,2,5,6]', 'ť');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (41, NULL, '[3,4,6]', 'ú');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (42, NULL, '[1,2,3,4,6]', 'ý');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (43, NULL, '[2,3,4,6]', 'ž');

INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (44, NULL, '[3,4,5,6]', '??'); -- čiselny znak
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (45, NULL, '[1,2,3,4,5,6]', 'pz');  -- plny znak
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (46, NULL, '[6]', 'vp');  -- znak velke pismeno
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (47, NULL, '[2]', ',');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (48, NULL, '[2,5,6]', '.');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (49, NULL, '[2,3,5]', '!');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (50, NULL, '[3,6]', '-');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (51, NULL, '[2,5]', ':');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (52, NULL, '[2,3,5,6]', '"');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (53, NULL, '[3]', 'apostrof');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (54, NULL, '[2,3,6]', '(');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (55, NULL, '[3,5,6]', ')');
INSERT INTO `brail_character` (`id`, `audio_file`, `brail_representation`, `letter`) VALUES (56, NULL, '[3,5]', '*');













-- METHODICAL LEARNING SEQUENCES
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 120, null, 1, 'ONE', 100000, 1, 80, 1);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 121, null, 1, 'TWO', 100000, 1, 80, 2);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 122, null, 1, 'THREE', 100000, 1, 80, 3);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 123, null, 1, 'FOUR', 100000, 1, 80, 4);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 124, null, 1, 'FIVE', 100000, 1, 80, 5);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 125, null, 1, 'SIX', 100000, 1, 80, 6);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 126, null, 1, 'SEVEN', 100000, 1, 80, 7);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 127, null, 1, 'EIGHT', 100000, 1, 80, 8);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 128, null, 1, 'NINE', 100000, 1, 80, 9);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 129, null, 1, 'TEN', 100000, 1, 80, 10);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 130, null, 1, 'ELEVEN', 100000, 1, 80, 11);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 131, null, 1, 'TWELVE', 100000, 1, 80, 12);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 132, null, 1, 'THIRTEEN', 100000, 1, 80, 13);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 133, null, 1, 'FOURTEEN', 100000, 1, 80, 14);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 134, null, 1, 'FIFTEEN', 100000, 1, 80, 15);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`, `allowed_test_retries`, `passing_rate_percentage`,`order`) VALUES ('MLS', 135, null, 1, 'SIXTEEN', 100000, 1, 80, 16);

-- a,b,l,e
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (122, null, b'0', 1, 120);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (123, null, b'0', 2, 120);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (124, null, b'0', 12, 120);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (125, null, b'0', 5, 120);

-- k,u
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (126, null, b'0', 11, 121);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (127, null, b'0', 21, 121);

-- c,o,m,i
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (128, null, b'0', 3, 122);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (129, null, b'0', 15, 122);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (130, null, b'0', 13, 122);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (131, null, b'0', 9, 122);

-- v,á,?,r
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (132, null, b'0', 3, 123);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (133, null, b'0', 27, 123);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (134, null, b'0', 29, 123);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (135, null, b'0', 18, 123);

-- , s ú p
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (136, null, b'0', 47, 124);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (137, null, b'0', 19, 124);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (138, null, b'0', 41, 124);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (139, null, b'0', 18, 124);

-- pz . í ?
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (140, null, b'0', 45, 125);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (141, null, b'0', 48, 125);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (142, null, b'0', 32, 125);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (143, null, b'0', 39, 125);


-- D, ?, N, G, É
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (144, null, b'0', 4, 126);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (145, null, b'0', 30, 126);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (146, null, b'0', 14, 126);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (147, null, b'0', 7, 126);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (148, null, b'0', 31, 126);


-- j t y
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (149, null, b'0', 10, 127);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (150, null, b'0', 20, 127);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (151, null, b'0', 25, 127);

-- !
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (152, null, b'0', 49, 128);

-- Z, F, ?, Ý, poml?ka
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (153, null, b'0', 26, 129);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (154, null, b'0', 6, 129);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (155, null, b'0', 35, 129);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (156, null, b'0', 42, 129);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (157, null, b'0', 50, 129);


-- H, ?, ?, dvojbodka
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (158, null, b'0', 8, 130);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (159, null, b'0', 40, 130);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (160, null, b'0', 43, 130);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (161, null, b'0', 51, 130);

--  Ô, ?, Ó, ?
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (162, null, b'0', 37, 131);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (163, null, b'0', 34, 131);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (164, null, b'0', 36, 131);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (165, null, b'0', 33, 131);

-- Ä, ?, X, úvodzovky
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (166, null, b'0', 27, 132);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (167, null, b'0', 38, 132);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (168, null, b'0', 24, 132);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (169, null, b'0', 52, 132);


-- apostrof
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (170, null, b'0', 53, 133);

-- q w ( )
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (171, null, b'0', 17, 134);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (172, null, b'0', 23, 134);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (173, null, b'0', 54, 134);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (174, null, b'0', 55, 134);

-- VP, ?Z, *
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (175, null, b'0', 45, 135);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (176, null, b'0', 44, 135);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (177, null, b'0', 56, 135);






-- LEARNING SEQUENCES
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('LS', 101, 2, 1, 'LEARNING_SEQUENCE_NAME', 100000);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('LS', 106, 3, 1, 'LEARNING_SEQUENCE_NAME', 100000);

INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (102, null, b'0', 1, 101);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (103, null, b'0', 2, 101);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (104, null, b'0', 12, 101);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (105, null, b'0', 5, 101);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (107, null, b'0', 11, 106);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (108, null, b'0', 21, 106);


-- TESTING SEQUENCES
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('TS', 107, 4, 1, 'TESTING_SEQUENCE_NAME', 100000);
INSERT INTO `sequence` (`dtype`, `id`, `external_id`, `allowed_retries`, `audio_message`, `timeout`) VALUES ('TS', 108, 5, 1, 'TESTING_SEQUENCE_NAME', 100000);

INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (109, null, b'0', 1, 107);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (110, null, b'0', 2, 107);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (111, null, b'0', 12, 107);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (112, null, b'0', 5, 107);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (113, null, b'0', 11, 108);
INSERT INTO `step` (`id`, `external_id`, `preserve_order`, `brail_character_id`, `sequence_id`) VALUES (114, null, b'0', 21, 108);


INSERT INTO hibernate_sequence (next_val) VALUES (115);
INSERT INTO hibernate_sequence (next_val) VALUES (116);
INSERT INTO hibernate_sequence (next_val) VALUES (117);
INSERT INTO hibernate_sequence (next_val) VALUES (118);
INSERT INTO hibernate_sequence (next_val) VALUES (119);



-- user_sequences
INSERT INTO `user_sequences` (`user_id`, `sequence_id`) VALUES (1, 101);
INSERT INTO `user_sequences` (`user_id`, `sequence_id`) VALUES (1, 106);
INSERT INTO `user_sequences` (`user_id`, `sequence_id`) VALUES (1, 107);
INSERT INTO `user_sequences` (`user_id`, `sequence_id`) VALUES (1, 108);


SET FOREIGN_KEY_CHECKS=1; --
