-- UUID PostgreSQL extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-----------------------------------------------------------------------------------------------------------------------

-- sequence для статуса заказов для ручного управления генерацией ID, вместо использования (big)serial
CREATE SEQUENCE order_statuses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- Статусы заказов
CREATE TABLE order_statuses
(
    id          BIGINT                      DEFAULT nextval('order_statuses_id_seq'::regclass) PRIMARY KEY,
    system_name varchar(255),
    active      BOOLEAN                     DEFAULT FALSE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);

-- Заказы
CREATE TABLE IF NOT EXISTS orders
(
    id           UUID               DEFAULT uuid_generate_v4() PRIMARY KEY,
    order_id     UUID      NOT NULL,
    customer_id  UUID      NOT NULL,
    total_amount numeric(25, 3),
    status       BIGINT REFERENCES order_statuses (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    created_by   VARCHAR(255),
    created_at   TIMESTAMP NOT NULL DEFAULT now(),
    updated_by   VARCHAR(255),
    updated_at   TIMESTAMP,
    deleted_by   VARCHAR(255),
    deleted_at   TIMESTAMP
);

-- Состав заказа, то есть услуги которые в заказе
CREATE TABLE order_items
(
    id         UUID                    DEFAULT uuid_generate_v4() PRIMARY KEY,
    order_id   UUID REFERENCES orders (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    item_id    BIGINT         NOT NULL,
    quantity   INT            NOT NULL,
    price      numeric(25, 3) NOT NULL,
    created_by VARCHAR(255)   NOT NULL,
    created_at TIMESTAMP      NOT NULL DEFAULT now(),
    updated_by VARCHAR(255),
    updated_at TIMESTAMP,
    deleted_by TIMESTAMP,
    deleted_at TIMESTAMP
);