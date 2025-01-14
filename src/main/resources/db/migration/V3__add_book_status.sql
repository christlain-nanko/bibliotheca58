-- Add a 'status' column to track the availability of books
ALTER TABLE books
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE';

-- Add a constraint to ensure only valid statuses are allowed
ALTER TABLE books
    ADD CONSTRAINT chk_book_status CHECK (status IN ('AVAILABLE', 'LENT'));