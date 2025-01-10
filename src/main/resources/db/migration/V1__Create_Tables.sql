-- Create Authors Table
CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE
);

-- Create Books Table
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    price DECIMAL(10, 2),
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

-- Create Members Table
CREATE TABLE members (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone_number VARCHAR(15)
);

-- Create Loans Table
CREATE TABLE loans (
    id SERIAL PRIMARY KEY,
    date_of_loan DATE NOT NULL,
    date_of_return DATE,
    member_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members (id)
);

-- Create Loan-Book Table
CREATE TABLE loan_books (
    loan_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (loan_id, book_id),
    FOREIGN KEY (loan_id) REFERENCES loans (id),
    FOREIGN KEY (book_id) REFERENCES books (id)
);