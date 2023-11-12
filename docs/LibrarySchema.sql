CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

DROP TABLE IF EXISTS `authors`;
CREATE TABLE `authors` (
  `Author_id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  PRIMARY KEY (`Author_id`)
);

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `Isbn` varchar(17) NOT NULL,
  `Title` varchar(255) NOT NULL,
  PRIMARY KEY (`Isbn`)
);

DROP TABLE IF EXISTS `book_authors`;
CREATE TABLE `book_authors` (
  `Author_id` int NOT NULL,
  `Isbn` varchar(17) NOT NULL,
  PRIMARY KEY (`Author_id`, `Isbn`),
  FOREIGN KEY (`Author_id`) REFERENCES `authors` (`Author_id`),
  FOREIGN KEY (`Isbn`) REFERENCES `book` (`Isbn`)
);

DROP TABLE IF EXISTS `book_loans`;
CREATE TABLE `book_loans` (
  `Loan_id` int NOT NULL AUTO_INCREMENT,
  `Isbn` varchar(17) NOT NULL,
  `Card_id` int NOT NULL,
  `Date_out` date NOT NULL,
  `Due_date` date NOT NULL,
  `Date_in` date DEFAULT NULL,
  PRIMARY KEY (`Loan_id`),
  FOREIGN KEY (`Isbn`) REFERENCES `book` (`Isbn`),
  FOREIGN KEY (`Card_id`) REFERENCES `borrower` (`Card_id`)
);

DROP TABLE IF EXISTS `borrower`;
CREATE TABLE `borrower` (
  `Card_id` int NOT NULL AUTO_INCREMENT,
  `Bname` varchar(100) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Phone` varchar(15) DEFAULT NULL,
  `Ssn` varchar(11) NOT NULL,
  PRIMARY KEY (`Card_id`),
  UNIQUE KEY `Ssn_UNIQUE` (`Ssn`)
);

DROP TABLE IF EXISTS `fines`;
CREATE TABLE `fines` (
  `Loan_id` int NOT NULL,
  `Fine_amt` decimal(4,2) NOT NULL,
  `Paid` tinyint(1) NOT NULL,
  PRIMARY KEY (`Loan_id`),
  FOREIGN KEY (`Loan_id`) REFERENCES `book_loans` (`Loan_id`)
);
