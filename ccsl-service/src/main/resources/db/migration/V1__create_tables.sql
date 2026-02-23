-- CCSL Schema: tokens and cards tables

CREATE TABLE tokens (
    id             BIGSERIAL    PRIMARY KEY,
    token          VARCHAR(100) NOT NULL UNIQUE,
    account_number VARCHAR(30)  NOT NULL
);

CREATE INDEX idx_tokens_account_number ON tokens(account_number);

CREATE TABLE cards (
    id             BIGSERIAL    PRIMARY KEY,
    account_number VARCHAR(30)  NOT NULL,
    card_number    VARCHAR(20)  NOT NULL,
    card_type      VARCHAR(20)  NOT NULL,
    card_category  VARCHAR(10)  NOT NULL,
    card_status    VARCHAR(10)  NOT NULL DEFAULT 'ACTIVE',
    expiry_date    DATE,
    issuer_bank    VARCHAR(50)
);

CREATE INDEX idx_cards_account_number ON cards(account_number);
CREATE INDEX idx_cards_card_number    ON cards(card_number);
CREATE INDEX idx_cards_category       ON cards(card_category);

