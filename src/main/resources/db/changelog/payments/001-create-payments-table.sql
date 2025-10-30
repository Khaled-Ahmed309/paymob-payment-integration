--liquibase formatted sql
--changeset khaled:001
CREATE SCHEMA IF NOT EXISTS payment;
SET search_path TO payment;


CREATE TABLE payment.payments(
id BIGSERIAL PRIMARY KEY,
payment_id VARCHAR(255),
product_id BIGINT NOT NULL,
price NUMERIC(19,2) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
status VARCHAR(50 ) CHECK (status in('PENDING','ACCEPTED','FAILED')),
method VARCHAR(50) CHECK (method in('PAYMOB','FAWRY')),
user_id BIGSERIAL

);