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
  
 CREATE TABLE `categories` (
`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(255) NOT NULL
 ); 
 
  CREATE TABLE `hotels` (
`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
`name` VARCHAR(255) NOT NULL
 ); 
 
 CREATE TABLE `appartments` (
`id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
`fk_category_id` INTEGER NOT NULL,
`fk_hotel_id` INTEGER NOT NULL,
`name` VARCHAR(255) NOT NULL,
CONSTRAINT `fk_appartments_to_categories` FOREIGN KEY (`fk_category_id`) REFERENCES `categories` (`id`)
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
 
 
 
 