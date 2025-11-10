-- Create user table
CREATE TABLE skyquest.user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255)
);

-- Create indexes for better performance
CREATE INDEX idx_user_username ON skyquest.user(username);
CREATE INDEX idx_user_email ON skyquest.user(email);