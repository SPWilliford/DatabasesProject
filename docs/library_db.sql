CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE BOOK (
    Isbn CHAR(10) NOT NULL PRIMARY KEY,
    Title VARCHAR(255) NOT NULL
);

CREATE TABLE AUTHORS (
    Author_id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE BOOK_AUTHORS (
    Author_id INT NOT NULL,
    Isbn CHAR(10) NOT NULL,
    PRIMARY KEY (Author_id, Isbn),
    FOREIGN KEY (Author_id) REFERENCES AUTHORS(Author_id),
    FOREIGN KEY (Isbn) REFERENCES BOOK(Isbn)
);

CREATE TABLE BORROWER (
    Card_id CHAR(8) NOT NULL PRIMARY KEY,
    Ssn CHAR(11) NOT NULL UNIQUE,
    Bname VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Phone VARCHAR(15)
);

CREATE TABLE BOOK_LOANS (
    Loan_id INT AUTO_INCREMENT PRIMARY KEY,
    Isbn CHAR(10) NOT NULL,
    Card_id CHAR(8) NOT NULL,
    Date_out DATE NOT NULL,
    Due_date DATE NOT NULL,
    Date_in DATE,
    FOREIGN KEY (Isbn) REFERENCES BOOK(Isbn),
    FOREIGN KEY (Card_id) REFERENCES BORROWER(Card_id)
);

CREATE TABLE FINES (
    Loan_id INT NOT NULL PRIMARY KEY,
    Fine_amt DECIMAL(6,2) NOT NULL,
    Paid BOOLEAN NOT NULL,
    FOREIGN KEY (Loan_id) REFERENCES BOOK_LOANS(Loan_id)
);
