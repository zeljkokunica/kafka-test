CREATE TABLE accounts (
    id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    number VARCHAR(255),
    reference VARCHAR(255),
    created_at DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at DATE,
    updated_by VARCHAR(255) NOT NULL
);
