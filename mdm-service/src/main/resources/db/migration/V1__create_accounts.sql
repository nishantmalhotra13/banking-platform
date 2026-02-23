-- MDM Schema: accounts table
-- Each row is a financial product held by a customer

CREATE TABLE accounts (
    id            BIGSERIAL    PRIMARY KEY,
    account_number VARCHAR(30)  NOT NULL UNIQUE,
    product_code  VARCHAR(20)  NOT NULL,
    phone         VARCHAR(20),
    name          VARCHAR(100),
    email         VARCHAR(100),
    address       VARCHAR(255),
    status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    date_opened   DATE,
    branch_code   VARCHAR(10)
);

CREATE INDEX idx_accounts_phone ON accounts(phone);
CREATE INDEX idx_accounts_email ON accounts(email);
CREATE INDEX idx_accounts_product_code ON accounts(product_code);
CREATE INDEX idx_accounts_status ON accounts(status);

