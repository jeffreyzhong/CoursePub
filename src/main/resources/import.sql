INSERT INTO user (id, email) VALUES (1, 'yujun_qin@brown.edu');
INSERT INTO user (id, email) VALUES (2, 'song_yang@brown.edu');
INSERT INTO user (id, email) VALUES (3, 'jeffrey_zhong@brown.edu');
INSERT INTO user (id, email) VALUES (4, 'xinyang_zhou@brown.edu');
INSERT INTO user (id, email) VALUES (5, 'john_jannotti@brown.edu');

INSERT INTO course (id, name) VALUES (1, 'Software Engineering');
INSERT INTO course (id, name) VALUES (2, 'Linear Algebra');

INSERT INTO registration (id, type, course_id, user_id) VALUES (1, 0, 1, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (2, 0, 1, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (3, 0, 1, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (4, 0, 1, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (5, 0, 2, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (6, 0, 2, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (7, 0, 2, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (8, 0, 2, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (9, 1, 1, 5);

INSERT INTO video (id, url, course_id) VALUES (1, 'https://google.com', 1);

INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES ('q1', 'Who is JJ', 'Heard people talking about him all the time', '1969-12-31 19:00:12', 1, 1, '2018-02-15 11:11:11');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES ('q2', 'What is GitHub', '', '1969-12-31 19:00:18', 1, 1, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES ('q3', 'Is there homework this week', 'help!!!', '1969-12-31 19:01:34', 2, 1, '2018-02-15 12:00:00');

INSERT INTO question_upvote (id, post_id, user_id) VALUES (1, 'q3', 2);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (2, 'q3', 3);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (3, 'q3', 4);

INSERT INTO response (id, body, user_id, question_id, post_time) VALUES ('r1', 'You are dumb jerry', 2, 'q2', '2018-04-12 05:58:19');
