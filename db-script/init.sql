DROP SCHEMA IF EXISTS `hotels`;
CREATE SCHEMA IF NOT EXISTS `hotels`
  CHARACTER SET `utf8`;

USE `hotels`;

CREATE TABLE `roles` (
  `id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role` VARCHAR(255) NOT NULL
);

CREATE TABLE `users` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_role_id`   BIGINT       NOT NULL,
  `login`        VARCHAR(255) NOT NULL,
  `password`     VARCHAR(255) NOT NULL,
  CONSTRAINT `fk_users_to_roles` FOREIGN KEY (`fk_role_id`) REFERENCES `roles` (`id`)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE `hotels` (
  `id`     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`   VARCHAR(255) NOT NULL,
  `city`   VARCHAR(255),
  `street` VARCHAR(255)
);


CREATE TABLE `restaurants` (
  `id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  CONSTRAINT `fk_restaurants_to_hotels` FOREIGN KEY (`id`) REFERENCES `hotels` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `appartments` (
  `id`               BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_hotel_id`      BIGINT       NOT NULL,
  `name`             VARCHAR(255) NOT NULL,
  `description`      VARCHAR(255),
  `guests_counts`    INT,
  `wi_fi`            BOOLEAN,
  `wc`               BOOLEAN,
  `tv`               BOOLEAN,
  `bar`              BOOLEAN,
  `kichen`           BOOLEAN,
  `appartments_type` VARCHAR(255) NOT NULL,
  CONSTRAINT `fk_appartments_to_hotels` FOREIGN KEY (`fk_hotel_id`) REFERENCES `hotels` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `orders` (
  `id`                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_users_id`       BIGINT NOT NULL,
  `fk_appartments_id` BIGINT NOT NULL,
  `start_date`        DATE   NOT NULL,
  `end_date`          DATE   NOT NULL,
  `version` TIMESTAMP,
  CONSTRAINT `fk_orders_to_user` FOREIGN KEY (`fk_users_id`) REFERENCES `users` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_orders_to_appartments` FOREIGN KEY (`fk_appartments_id`) REFERENCES `appartments` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

/*CREATE TABLE `dishes_categories` (
  `id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL
);
*/
CREATE TABLE `dishes` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_dishes_categories` BIGINT       NOT NULL,
  `name`                 VARCHAR(255) NOT NULL
  /* CONSTRAINT `fk_dishes_to_dishes_categories` FOREIGN KEY (`fk_dishes_categories`) REFERENCES `dishes_categories` (`id`)
     ON UPDATE CASCADE
     ON DELETE CASCADE*/
);

CREATE TABLE `dishes_restaurants` (
  `fk_dishes_id` BIGINT NOT NULL,
  `fk_restaurants_id` BIGINT NOT NULL,
  PRIMARY KEY (`fk_dishes_id`, `fk_restaurants_id`),
  CONSTRAINT `fk_dishes_restaurants_to_dishes` FOREIGN KEY (`fk_dishes_id`) REFERENCES `dishes` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_dishes_restaurants_to_hotels` FOREIGN KEY (`fk_restaurants_id`) REFERENCES `restaurants` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `dish_orders` (
  `id`                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_users_id`       BIGINT NOT NULL,
  `fk_appartments_id` BIGINT NOT NULL,
  `order_time`        DATE   NOT NULL,
  CONSTRAINT `fk_dish_orders_to_user` FOREIGN KEY (`fk_users_id`) REFERENCES `users` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_dish_orders_to_appartments` FOREIGN KEY (`fk_appartments_id`) REFERENCES `appartments` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `dish_orders_details` (
  `id`                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_dish_orders_id` BIGINT NOT NULL,
  `fk_dishes_id`      BIGINT NOT NULL,
  `count`             INT    NOT NULL,
  CONSTRAINT `fk_orders_details_to_dish_orders` FOREIGN KEY (`fk_dishes_id`) REFERENCES `dish_orders` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

-- test data --
INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('USER');

INSERT INTO users (fk_role_id, login, password) VALUES (2, 'user', 'user');
INSERT INTO users (fk_role_id, login, password) VALUES (1, 'admin', 'admin');

INSERT INTO hotels (name, city, street) VALUES ('Crowne Plaza', 'Минск', 'Кирова 13');
INSERT INTO hotels (name, city, street) VALUES ('Minsk Marriott Hotel', 'Минск', 'пр Победителей 20');
INSERT INTO hotels (name, city, street) VALUES ('Renaissance Minsk Hotel', 'Минск', 'пр Держинского 1');

INSERT INTO appartments (fk_hotel_id, name, description, appartments_type) VALUES (1, 'name', 'desc', 'econom');
INSERT INTO appartments (fk_hotel_id, name, description, appartments_type) VALUES (1, 'name1', 'desc1', 'standart');
INSERT INTO appartments (fk_hotel_id, name, description, appartments_type) VALUES (1, 'name3', 'desc3', 'lux');
INSERT INTO appartments (fk_hotel_id, name, description, appartments_type) VALUES (1, 'name2', 'desc2', 'econom');

 
 