INSERT INTO user (id, email) VALUES (1, 'yujun_qin@brown.edu');
INSERT INTO user (id, email) VALUES (2, 'song_yang@brown.edu');
INSERT INTO user (id, email) VALUES (3, 'jeffrey_zhong@brown.edu');
INSERT INTO user (id, email) VALUES (4, 'xinyang_zhou@brown.edu');
INSERT INTO user (id, email) VALUES (5, 'john_jannotti@brown.edu');
INSERT INTO user (id, email) VALUES (6, 'jzhong2468@gmail.com');
INSERT INTO user (id, email) VALUES (7, 'yjnqin@gmail.com');

INSERT INTO course (id, name) VALUES (1, 'Linear Algebra');
INSERT INTO course (id, name) VALUES (2, 'Software Engineering');

INSERT INTO registration (id, type, course_id, user_id) VALUES (1, 1, 1, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (2, 1, 1, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (3, 1, 1, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (4, 1, 1, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (5, 1, 2, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (6, 1, 2, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (7, 1, 2, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (8, 1, 2, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (9, 1, 2, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (10, 0, 1, 6);
INSERT INTO registration (id, type, course_id, user_id) VALUES (11, 0, 1, 7);

INSERT INTO video (id, url, course_id) VALUES (1, 'https://google.com', 1);

INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (1, 'Who is JJ', 'Heard people talking about him all the time', '1969-12-31 19:00:12', 1, 1, '2018-02-15 11:11:11');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (2, 'What is GitHub', '', '1969-12-31 19:00:18', 1, 1, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (3, 'Is there homework this week', 'help!!!', '1969-12-31 19:01:34', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (4, 'We will miss JJ', 'not', '1969-12-31 19:00:34', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (5, 'No Homework This Week!', 'Have fun!', '1969-12-31 19:01:00', 3, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (6, 'Gary[heart eye emoji]', '', '1969-12-31 19:00:42', 1, 1, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (7, 'Jeff is bae', 'help!!!', '1969-12-31 19:02:09', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (8, 'Jerry is God', 'not', '1969-12-31 19:00:57', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (9, 'Song[fire emoji]', 'Have fun!', '1969-12-31 19:02:00', 3, 1, '2018-02-15 12:00:00');

INSERT INTO question_upvote (id, post_id, user_id) VALUES (1, 3, 2);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (2, 3, 3);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (3, 3, 4);

INSERT INTO response (id, body, user_id, question_id, post_time) VALUES (1, 'You are dumb jerry', 2, 2, '2018-04-12 05:58:19');

INSERT INTO instructor_answer (id, body, user_id, question_id, post_time) VALUES (1, 'You are officially dumb jerry', 5, 2, '2018-04-13 04:12:57');
