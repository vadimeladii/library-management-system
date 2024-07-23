-- Create Authors table
CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         biography TEXT,
                         birth_date DATE
);

-- Create Books table
CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       isbn VARCHAR(13) UNIQUE NOT NULL,
                       published_date DATE,
                       status VARCHAR(10) NOT NULL,
                       author_id INTEGER,
                       FOREIGN KEY (author_id) REFERENCES authors (id)
);

-- Create Members table
CREATE TABLE members (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         membership_date DATE
);

-- Create BorrowRecords table
CREATE TABLE borrow_records (
                                id SERIAL PRIMARY KEY,
                                member_id INTEGER,
                                book_id INTEGER,
                                borrow_date DATE,
                                return_date DATE,
                                FOREIGN KEY (member_id) REFERENCES members (id),
                                FOREIGN KEY (book_id) REFERENCES books (id)
);

-- Insert initial data into Authors table
INSERT INTO authors (name, biography, birth_date) VALUES
                                                      ('Author One', 'Biography of Author One', '1970-01-01'),
                                                      ('Author Two', 'Biography of Author Two', '1980-02-02'),
                                                      ('Author Three', 'Biography of Author Three', '1990-03-03'),
                                                      ('Author Four', 'Biography of Author Four', '1985-04-04'),
                                                      ('Author Five', 'Biography of Author Five', '1975-05-05');

-- Insert initial data into Books table
INSERT INTO books (title, isbn, published_date, status, author_id) VALUES
                                                                       ('Book One', '1234567890123', '2000-03-03', 'AVAILABLE', 1),
                                                                       ('Book Two', '1234567890124', '2001-04-04', 'AVAILABLE', 2),
                                                                       ('Book Three', '1234567890125', '2002-05-05', 'BORROWED', 3),
                                                                       ('Book Four', '1234567890126', '2003-06-06', 'AVAILABLE', 4),
                                                                       ('Book Five', '1234567890127', '2004-07-07', 'AVAILABLE', 5),
                                                                       ('Book Six', '1234567890128', '2005-08-08', 'BORROWED', 1),
                                                                       ('Book Seven', '1234567890129', '2006-09-09', 'AVAILABLE', 2),
                                                                       ('Book Eight', '1234567890130', '2007-10-10', 'AVAILABLE', 3),
                                                                       ('Book Nine', '1234567890131', '2008-11-11', 'AVAILABLE', 4),
                                                                       ('Book Ten', '1234567890132', '2009-12-12', 'BORROWED', 5);

-- Insert initial data into Members table
INSERT INTO members (name, email, membership_date) VALUES
                                                       ('Member One', 'member.one@example.com', '2021-05-05'),
                                                       ('Member Two', 'member.two@example.com', '2021-06-06'),
                                                       ('Member Three', 'member.three@example.com', '2021-07-07'),
                                                       ('Member Four', 'member.four@example.com', '2021-08-08'),
                                                       ('Member Five', 'member.five@example.com', '2021-09-09');

-- Insert initial data into BorrowRecords table
INSERT INTO borrow_records (member_id, book_id, borrow_date, return_date) VALUES
                                                                              (1, 1, '2021-07-07', NULL),
                                                                              (2, 2, '2021-08-08', NULL),
                                                                              (3, 3, '2021-09-09', NULL),
                                                                              (4, 4, '2021-10-10', NULL),
                                                                              (5, 5, '2021-11-11', NULL),
                                                                              (1, 6, '2021-12-12', NULL),
                                                                              (2, 7, '2022-01-01', NULL),
                                                                              (3, 8, '2022-02-02', NULL),
                                                                              (4, 9, '2022-03-03', NULL),
                                                                              (5, 10, '2022-04-04', NULL);