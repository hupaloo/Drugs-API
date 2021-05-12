create SCHEMA `drugs` ;

create TABLE `drugs`.`ingredients` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ingredient_id`),
  UNIQUE INDEX `ingredient_name_UNIQUE` (`ingredient_name` ASC) VISIBLE);

create TABLE `drugs`.`drugs` (
  `drug_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `applicant_details` VARCHAR(500) NULL,
  `producer_details` VARCHAR(500) NULL,
  `registration_certificate_number` VARCHAR(20) NULL,
  `atc_code` VARCHAR(12) NOT NULL,
  `compendium_url` VARCHAR(500) NULL,
  `expiration_date` VARCHAR(50) NULL,
  `international_name` VARCHAR(100) NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`drug_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

create TABLE `drugs`.`ingredient_groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(200) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON update CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`),
  UNIQUE INDEX `group_name_UNIQUE` (`group_name` ASC) VISIBLE);

create TABLE `drugs`.`patients` (
  `patient_id` INT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON update CURRENT_TIMESTAMP);

  CREATE TABLE `drugs`.`tolerance_finder` (
  `run_id` INT NOT NULL AUTO_INCREMENT,
  `atc_codes` VARCHAR(500) NOT NULL,
  `ingredient_names` VARCHAR(500) NULL,
  `run_status` VARCHAR(45) NOT NULL,
  `patient_id` INT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`run_id`),
  UNIQUE INDEX `run_id_UNIQUE` (`run_id` ASC) VISIBLE,
FOREIGN KEY (`patient_id`) REFERENCES `drugs`.`patients`(`patient_id`));


create TABLE `drugs`.`patients_ingredients` (
`patient_id` INT NOT NULL,
`ingredient_id` INT NOT NULL,
FOREIGN KEY (`ingredient_id`) REFERENCES `drugs`.`ingredients`(`ingredient_id`),
  FOREIGN KEY (`patient_id`) REFERENCES `drugs`.`patients`(`patient_id`)
  );

create TABLE `drugs`.`ingredients_group_belonging` (
`group_id` INT NOT NULL,
`ingredient_id` INT NOT NULL,
FOREIGN KEY (`ingredient_id`) REFERENCES `drugs`.`ingredients`(`ingredient_id`),
  FOREIGN KEY (`group_id`) REFERENCES `drugs`.`ingredient_groups`(`group_id`)
  );

create TABLE `drugs`.`drugs_ingredients` (
`drug_id` INT NOT NULL,
`ingredient_id` INT NOT NULL,
FOREIGN KEY (`ingredient_id`) REFERENCES `drugs`.`ingredients`(`ingredient_id`),
  FOREIGN KEY (`drug_id`) REFERENCES `drugs`.`drugs`(`drug_id`)
  );
