INSERT INTO user (id, email) VALUES (1, 'yujun_qin@brown.edu');
INSERT INTO user (id, email) VALUES (2, 'song_yang@brown.edu');
INSERT INTO user (id, email) VALUES (3, 'jeffrey_zhong@brown.edu');
INSERT INTO user (id, email) VALUES (4, 'xinyang_zhou@brown.edu');
INSERT INTO user (id, email) VALUES (5, 'john_jannotti@brown.edu');
INSERT INTO user (id, email) VALUES (6, 'jzhong2468@gmail.com');
INSERT INTO user (id, email) VALUES (7, 'yjnqin@gmail.com');
INSERT INTO user (id, email) VALUES (8, 'garyzhou98@gmail.com');

INSERT INTO course (id, name) VALUES (1, 'Linear Algebra');
INSERT INTO course (id, name) VALUES (2, 'Software Engineering');
INSERT INTO course (id, name) VALUES (3, 'Introduction to Matrices');
INSERT INTO course (id, name) Values (4, 'Macroeconomics');
INSERT INTO course (id, name) Values (5, 'Machine Learning');


INSERT INTO registration (id, type, course_id, user_id) VALUES (1, 1, 1, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (2, 1, 2, 1);
INSERT INTO registration (id, type, course_id, user_id) VALUES (3, 1, 1, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (4, 1, 2, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (5, 1, 5, 2);
INSERT INTO registration (id, type, course_id, user_id) VALUES (6, 1, 1, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (7, 1, 2, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (8, 1, 5, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (9, 1, 1, 4);


INSERT INTO registration (id, type, course_id, user_id) VALUES (10, 0, 1, 6);
INSERT INTO registration (id, type, course_id, user_id) VALUES (11, 0, 1, 7);
INSERT INTO registration (id, type, course_id, user_id) VALUES (12, 1, 2, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (13, 1, 5, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (14, 1, 1, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (15, 1, 2, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (16, 1, 3, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (17, 1, 4, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (18, 1, 5, 5);
INSERT INTO registration (id, type, course_id, user_id) VALUES (19, 1, 4, 3);
INSERT INTO registration (id, type, course_id, user_id) VALUES (20, 1, 3, 4);
INSERT INTO registration (id, type, course_id, user_id) VALUES (21, 1, 4, 8);
INSERT INTO registration (id, type, course_id, user_id) VALUES (22, 1, 5, 8);

INSERT INTO video (id, url, course_id) VALUES (1, "https://www.youtube.com/embed/kjBOesZCoqc?rel=0&enablejsapi=1", 1);
INSERT INTO video (id, url, course_id) VALUES (2, "https://www.youtube.com/embed/O753uuutqH8?rel=0&enablejsapi=1", 2);
INSERT INTO video (id, url, course_id) VALUES (3, "https://www.youtube.com/embed/xyAuNHPsq-g?rel=0&enablejsapi=1", 3);
INSERT INTO video (id, url, course_id) VALUES (4, "https://www.youtube.com/embed/oLhohwfwf_U?rel=0&enablejsapi=1", 4);
INSERT INTO video (id, url, course_id) VALUES (5, "https://www.youtube.com/embed/h0e2HAPTGF4?rel=0&enablejsapi=1", 5);

INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (1, 'We love JJ', 'Please don\'t leave!', '1969-12-31 19:00:12', 1, 1, '2018-02-15 11:11:11');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (2, 'Why is linear algebra important?', 'Where is it used?', '1969-12-31 19:00:18', 1, 1, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (3, 'Is there homework this week', 'help!!!', '1969-12-31 19:01:34', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (4, 'What\'s the point of linear algebra?', 'what is it?', '1969-12-31 19:00:24', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (5, 'Why is it so elementary?', 'Pourquoi?', '1969-12-31 19:00:22', 3, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (6, 'what is matrix multiplication?', 'You can do that?', '1969-12-31 19:00:30', 1, 1, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (7, 'what is the geometric representation of a matrix?', 'Never heard of it, what is it?', '1969-12-31 19:01:03', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (8, 'How is the geometric understanding applied in these fields? ', 'Through what applications?', '1969-12-31 19:01:37', 2, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (9, 'People use linear algebra in day to day work?', 'Not how I imageined my post-undergrad time', '1969-12-31 19:01:45', 3, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (10, 'Is that taylor’s series for sine??', 'Do not want to see that', '1969-12-31 19:02:18', 3, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (11, 'But how does sine works in physics tho??', 'Asking for a friend', '1969-12-31 19:02:37', 3, 1, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (12, 'So linear algebra can be understood like trig??', 'Woahh', '1969-12-31 19:03:14', 3, 1, '2018-02-15 12:00:00');


INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (13, 'How many lines of code do programmers write in a day?', 'Is it almost the same as writing an essay?', '1969-12-31 19:00:12', 1, 2, '2018-02-15 11:11:11');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (14, 'What is the best sorting algorithm?', 'There are so many sorting algorithms and I am wondering which one is best.', '1969-12-31 19:01:18', 1, 2, '2018-02-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (15, 'I do not understand the idea of parent and children here.','Is this just an analogy for something else?', '1969-12-31 19:02:34', 2, 2, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (16, 'How do you create a function?','I\'\ m confused as to the syntax around writing a function in Java.', '1969-12-31 19:03:34', 2, 2, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (17, 'What is the best programming language to write in?', 'Java, Python, C, Ruby, C++, C#...?', '1969-12-31 19:04:00', 3, 2, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (18, 'What is the best IDE to write in?', 'Eclipse, IntelliJ, what else?', '1969-12-31 19:05:42', 1, 2, '2018-04-15 11:13:28');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (19, 'How much should I be commenting in my code?', 'If I am the only one that is going to read it, is it still that important?', '1969-12-31 19:06:09', 2, 2, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (20, 'Are QA\'\ s the same as software engineers?', 'They seem to be doing similar things...', '1969-12-31 19:07:57', 2, 2, '2018-02-15 12:00:00');
INSERT INTO question (id, title, body, video_time, user_id, video_id, post_time) VALUES (21, 'What is the best resource to start learning how to code?', 'I heard CoursePub is pretty good!!', '1969-12-31 19:08:23', 3, 2, '2018-02-15 12:00:00');

INSERT INTO question_upvote (id, post_id, user_id) VALUES (1, 3, 2);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (2, 3, 3);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (3, 3, 4);
INSERT INTO question_upvote (id, post_id, user_id) VALUES (4, 6, 1);


INSERT INTO response (id, body, user_id, question_id, post_time) VALUES (1, 'No Homework This Week!', 3, 3, '2018-04-12 05:58:19');
INSERT INTO instructor_answer (id, body, user_id, question_id, post_time) VALUES (1, 'Linear Algebra is used throughout many STEM disciplines', 5, 2, '2018-04-13 04:12:57');
INSERT INTO student_answer (id, body, user_id, question_id, post_time) VALUES (1, 'Yes we do!!', 7, 1, '2018-05-01 07:17:32');

INSERT INTO response (body, user_id, question_id, post_time) VALUES ('It’s actually used in many subjects', 2, 2, '2018-04-12 05:58:19');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('It is a prereq just take it man', 2, 2, '2018-04-12 05:58:30');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('It is one of the fundamental studies in mathematics', 2, 2, '2018-04-12 05:59:19');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Yes you can! And you will learn it in this class!', 2, 6, '2018-04-12 05:59:30');

INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Maybe it is useful in computer vision', 3, 2, '2018-05-01 02:23:14');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('It is the pillar of many stem fields', 3, 4, '2018-05-01 02:29:26');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Somebody asked a similar question', 3, 4, '2018-05-01 02:29:26');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Stay tuned, this will be discussed in the next video', 3, 6, '2018-05-05 13:13:13');
INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Yeah', 3, 12, '2018-05-06 14:14:13');

INSERT INTO response (body, user_id, question_id, post_time) VALUES ('Similar questions have been asked, check them out!', 4, 5, '2018-05-01 02:29:26');

INSERT INTO response (body, user_id, question_id, post_time) VALUES ('A matrix is actually a linear transformation like what you learned in high school functions class!', 4, 7, '2018-05-01 02:29:26');

INSERT INTO response (body, user_id, question_id, post_time) VALUES ('But it\'\ s just worth soooo much more than I think it should.' , 2, 11, '2018-04-12 05:58:19');


INSERT INTO instructor_answer (body, user_id, question_id, post_time) VALUES ('Matrix multiplication is when you multiply two matrices together by taking one value in the first matrix and multiplying it by another value in the second matrix and creating a new value in the resulting matrix!', 5, 6, '2018-04-13 04:12:57');
INSERT INTO instructor_answer (body, user_id, question_id, post_time) VALUES ('The geometric representation of a matrix is a collection of vectors in m dimensional space where m is the number of rows there are in the matrix', 5, 7, '2018-04-13 04:12:57');
INSERT INTO instructor_answer (body, user_id, question_id, post_time) VALUES ('Linear Algebra is a super important concept and it is used throughout physics, graphics, machine learning, etc!', 5, 4, '2018-04-13 04:12:57');
INSERT INTO instructor_answer (body, user_id, question_id, post_time) VALUES ('It depends on what job/industry you are referring to. If you work somewhere in the STEM word, chances are linear algebra will be a core concept of your line of work.', 5, 9, '2018-04-13 04:12:57');
