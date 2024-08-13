-- Вставка данных в таблицу users
INSERT INTO users (email) VALUES ('user1@example.com');
INSERT INTO users (email) VALUES ('user2@example.com');
INSERT INTO users (email) VALUES ('user3@example.com');
INSERT INTO users (email) VALUES ('user4@example.com');
INSERT INTO users (email) VALUES ('user5@example.com');

-- Вставка данных в таблицу publications (пример)
INSERT INTO publications (content, publication_time, user_id, notified, notification_type) VALUES ('First publication', '2023-10-01', 1, FALSE, 'NEW_PUBLICATION');
INSERT INTO publications (content, publication_time, user_id, notified, notification_type) VALUES ('Second publication', '2023-10-02', 2, FALSE, 'NEW_PUBLICATION');
INSERT INTO publications (content, publication_time, user_id, notified, notification_type) VALUES ('Third publication', '2023-10-03', 3, FALSE, 'NEW_PUBLICATION');
INSERT INTO publications (content, publication_time, user_id, notified, notification_type) VALUES ('Fourth publication', '2023-10-04', 4, FALSE, 'NEW_PUBLICATION');
INSERT INTO publications (content, publication_time, user_id, notified, notification_type) VALUES ('Fifth publication', '2023-10-05', 5, FALSE, 'NEW_PUBLICATION');

-- Вставка данных в таблицу notifications (пример)
INSERT INTO notifications (content, notification_time, notified, notification_type, user_id) VALUES ('First notification', '2023-10-01', FALSE, 'NEW_PUBLICATION', 1);
INSERT INTO notifications (content, notification_time, notified, notification_type, user_id) VALUES ('Second notification', '2023-10-02', FALSE, 'NEW_PUBLICATION', 2);
INSERT INTO notifications (content, notification_time, notified, notification_type, user_id) VALUES ('Third notification', '2023-10-03', FALSE, 'NEW_PUBLICATION', 3);
INSERT INTO notifications (content, notification_time, notified, notification_type, user_id) VALUES ('Fourth notification', '2023-10-04', FALSE, 'NEW_PUBLICATION', 4);
INSERT INTO notifications (content, notification_time, notified, notification_type, user_id) VALUES ('Fifth notification', '2023-10-05', FALSE, 'NEW_PUBLICATION', 5);

-- Вставка данных в таблицу user_subscriptions (пример)
INSERT INTO user_subscriptions (user_id, subscribed_user_id) VALUES (1, 2);
INSERT INTO user_subscriptions (user_id, subscribed_user_id) VALUES (1, 3);
INSERT INTO user_subscriptions (user_id, subscribed_user_id) VALUES (2, 1);
INSERT INTO user_subscriptions (user_id, subscribed_user_id) VALUES (2, 3);
INSERT INTO user_subscriptions (user_id, subscribed_user_id) VALUES (3, 1);
