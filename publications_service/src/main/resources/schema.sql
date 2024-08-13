-- Создание таблицы users
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы publications
CREATE TABLE publications (
                              publication_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              content TEXT NOT NULL,
                              publication_time DATE NOT NULL,
                              user_id BIGINT NOT NULL,
                              notified BOOLEAN NOT NULL,
                              notification_type VARCHAR(50) NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Создание таблицы notifications
CREATE TABLE notifications (
                               notification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               content TEXT NOT NULL,
                               notification_time DATE NOT NULL,
                               notified BOOLEAN NOT NULL,
                               notification_type VARCHAR(50) NOT NULL,
                               user_id BIGINT NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Создание таблицы user_subscriptions
CREATE TABLE user_subscriptions (
                                    user_id BIGINT NOT NULL,
                                    subscribed_user_id BIGINT NOT NULL,
                                    PRIMARY KEY (user_id, subscribed_user_id),
                                    FOREIGN KEY (user_id) REFERENCES users(user_id),
                                    FOREIGN KEY (subscribed_user_id) REFERENCES users(user_id)
);
