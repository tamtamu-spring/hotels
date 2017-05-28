DROP SCHEMA IF EXISTS `hotels`;
CREATE SCHEMA IF NOT EXISTS `hotels`
  CHARACTER SET `utf8`;

USE `hotels`;

CREATE TABLE `roles` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role` VARCHAR(255) NOT NULL
);

CREATE TABLE `users` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_role_id` INTEGER NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `block_status` BOOLEAN NOT NULL,
  CONSTRAINT `fk_users_to_roles` FOREIGN KEY (`fk_role_id`) REFERENCES `roles` (`id`)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE `appart_categories` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL
);

CREATE TABLE `hotels` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL
);

CREATE TABLE `appartments` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_appart_category_id` INTEGER NOT NULL,
  `fk_hotel_id` INTEGER NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255),
  CONSTRAINT `fk_appartments_to_appart_categories` FOREIGN KEY (`fk_appart_category_id`) REFERENCES `appart_categories` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_appartments_to_hotels` FOREIGN KEY (`fk_hotel_id`) REFERENCES `hotels` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `orders` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_users_id` INTEGER NOT NULL,
  `fk_appartments_id` INTEGER NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  CONSTRAINT `fk_orders_to_user` FOREIGN KEY (`fk_users_id`) REFERENCES `users` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_orders_to_appartments` FOREIGN KEY (`fk_appartments_id`) REFERENCES `appartments` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `dishes_categories` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL
);

CREATE TABLE `dishes` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_dishes_categories` INTEGER NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  CONSTRAINT `fk_dishes_to_dishes_categories` FOREIGN KEY (`fk_dishes_categories`) REFERENCES `dishes_categories` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE `dish_orders` (
  `id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fk_users_id` INTEGER NOT NULL,
  `fk_dishes_id` INTEGER NOT NULL,
  `fk_hotels_id` INTEGER NOT NULL,
  `start_date` DATE NOT NULL,

  CONSTRAINT `fk_dish_orders_to_user` FOREIGN KEY (`fk_users_id`) REFERENCES `users` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_dish_orders_to_dishes` FOREIGN KEY (`fk_dishes_id`) REFERENCES `dishes` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_dish_orders_to_hotels` FOREIGN KEY (`fk_hotels_id`) REFERENCES `hotels` (`id`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);



-- test data --
insert into hotels (name) values ("one");

insert into appart_categories (name) values ("one");

insert into appartments (fk_appart_category_id, fk_hotel_id, name, description) VALUES (1,1,'name', 'desc');
insert into appartments (fk_appart_category_id, fk_hotel_id, name, description) VALUES (1,1,'name1', 'desc1');
insert into appartments (fk_appart_category_id, fk_hotel_id, name, description) VALUES (1,1,'name2', 'desc2');

 
 