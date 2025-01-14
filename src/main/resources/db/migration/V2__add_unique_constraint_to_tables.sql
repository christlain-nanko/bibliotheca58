ALTER TABLE books
    ADD CONSTRAINT unique_book_title_author UNIQUE (title, author_id);

ALTER TABLE members
    ADD CONSTRAINT unique_member_username UNIQUE (username);

ALTER TABLE members
    ADD CONSTRAINT unique_member_email UNIQUE (email);

ALTER TABLE authors
    ADD CONSTRAINT unique_author_name_dob UNIQUE (name, date_of_birth);

