# CS 4347 Programming Project

## Description
This project is a Library Management System developed for the CS-4347 Database Systems course. It is designed to assist librarians in managing library operations and is not intended for book borrowers. yada yada yada complete this later
## Current Status
I got the books csv read and loaded into the database tables for book, authors and book_authors. I think it works correctly, but it's pretty slow to read
and populate the tables. Will look into that later.
## Database
We are using MySQL server. The project is configured to just run a local instance  
with username set to root and no password. You can change that if you need by modifying the application.properties file located in src/main/resources.
The sql file called LibrarySchema.sql will create the database and tables. It's not the original one that we used because i got it mixed up and wrote the program with this one so sorry about that
## Project Structure
### Packages
#### entity:
Java classes representing database tables.
#### fileparser:
Classes for reading data files and loading data into the database.
#### repository:
Interfaces for database operations, based on Spring Data JPA
#### service:
Classes containing business logic, using repositories to interact with the database. Basically we need
to figure out exactly what methods we will need for our front end and implement them in this class. 
