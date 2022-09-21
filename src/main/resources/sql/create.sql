-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cash_register
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cash_register` ;

-- -----------------------------------------------------
-- Schema cash_register
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cash_register` DEFAULT CHARACTER SET utf8 ;
USE `cash_register` ;

-- -----------------------------------------------------
-- Table `cash_register`.`unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`unit` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`unit` (
  `name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`product` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `unit_name` VARCHAR(10) NOT NULL,
  `price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`, `unit_name`),
  INDEX `fk_goods_unit1_idx` (`unit_name` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_goods_unit1`
    FOREIGN KEY (`unit_name`)
    REFERENCES `cash_register`.`unit` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`warehouse`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`warehouse` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`warehouse` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` DECIMAL(10,3) UNSIGNED NOT NULL,
  `product_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `product_id`),
  INDEX `fk_warehouse_goods1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_warehouse_goods1`
    FOREIGN KEY (`product_id`)
    REFERENCES `cash_register`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`role` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`user` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(100) NULL,
  `role_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `role_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_users_roles1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `cash_register`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`order` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`order` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `time` DATETIME NULL,
  `totalAmount` DECIMAL(15,2) NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_ordes_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_ordes_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cash_register`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register`.`order_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register`.`order_details` ;

CREATE TABLE IF NOT EXISTS `cash_register`.`order_details` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` INT UNSIGNED NOT NULL,
  `product_id` INT UNSIGNED NOT NULL,
  `quantity` DECIMAL(10,3) UNSIGNED NULL,
  `price` DECIMAL(10,2) UNSIGNED NULL,
  PRIMARY KEY (`id`, `order_id`, `product_id`),
  INDEX `fk_order_details_ordes1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_details_goods1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_details_ordes1`
    FOREIGN KEY (`order_id`)
    REFERENCES `cash_register`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_details_goods1`
    FOREIGN KEY (`product_id`)
    REFERENCES `cash_register`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
