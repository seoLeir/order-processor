-- Файл .sql с пользователями

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- Пользователи для входа в систему
CREATE TABLE users
(
    id            BIGINT               DEFAULT nextval('users_id_seq'::regclass) PRIMARY KEY,
    login         VARCHAR(50) NOT NULL UNIQUE,
    phone_number  varchar(255),
    password_hash VARCHAR(60),
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    email         VARCHAR(191) UNIQUE,
    image_url     VARCHAR(256),
    activated     BOOLEAN     NOT NULL DEFAULT FALSE,
    deleted       BOOLEAN              DEFAULT false,
    created_by   VARCHAR(255),
    created_at   TIMESTAMP NOT NULL DEFAULT now(),
    updated_by   VARCHAR(255),
    updated_at   TIMESTAMP,
    deleted_by   VARCHAR(255),
    deleted_at   TIMESTAMP
);

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- Роли в системе
CREATE TABLE roles
(
    id          BIGINT    DEFAULT nextval('roles_id_seq'::regclass) PRIMARY KEY,
    system_name VARCHAR(255),
    created_at  TIMESTAMP DEFAULT now()
);

CREATE SEQUENCE permission_entities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- сущности: заказы
CREATE TABLE permission_entities
(
    id BIGINT DEFAULT nextval('permission_entities_id_seq'::regclass) PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE SEQUENCE permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

-- Общие действия над сущностями: CREATE, UPDATE, VIEW, DELETE
CREATE TABLE permissions
(
    id BIGINT DEFAULT nextval('permissions_id_seq'::regclass) PRIMARY KEY,
    system_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE SEQUENCE permission_entity_role_rules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

CREATE TABLE permission_entity_role_rules
(
    id BIGINT DEFAULT nextval('permission_entity_role_rules_id_seq'::regclass) PRIMARY KEY,
    permission_entity BIGINT REFERENCES permission_entities(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    permission BIGINT REFERENCES permissions(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    description VARCHAR(255),
    authority_id BIGINT REFERENCES authorities(id),
    role_id BIGINT REFERENCES roles(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE SEQUENCE authorities_id_seq
START WITH 1
INCREMENT BY 1
NO CYCLE;

CREATE TABLE authorities
(
    id BIGINT DEFAULT nextval('authorities_id_seq'::regclass) PRIMARY KEY,
    system_name VARCHAR(255) UNIQUE
);

drop table permission_entity_role_rules;

CREATE TABLE user_permissions
(
    authority VARCHAR(255) REFERENCES authorities(system_name),
    user_id BIGINT REFERENCES users(id),
    PRIMARY KEY (user_id, authority)
);