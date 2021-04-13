-- Магия сегодня
-- CREATE SEQUENCE hibernate_sequence START 1;

-- Создание таблицы
CREATE TYPE gender_type AS ENUM('MALE', 'FEMALE');
CREATE TYPE role_type AS ENUM(
    'ADMINISTRATOR',
    'DIRECTOR',
    'ACCOUNTANT',
    'REGULAR',
    'REGULAR_UNEMPLOYED'
    );
CREATE TABLE IF NOT EXISTS users
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(200) NOT NULL ,
    surname     VARCHAR(200) NOT NULL ,
    email       VARCHAR(254) NOT NULL ,
    password    VARCHAR(254) NOT NULL ,
    phone       VARCHAR(20)  NOT NULL ,
    gender      gender_type  NOT NULL ,
    company_id  UUID,
    role        role_type    NOT NULL DEFAULT 'REGULAR_UNEMPLOYED'::role_type
    );


CREATE TYPE companyStatus_type AS ENUM(
    'DRAFT',
    'READY_TO_VERIFY',
    'APPROVED',
    'NOT_APPROVED',
    'PUBLISHED'
    );
CREATE TABLE IF NOT EXISTS companies
(
    id          UUID               PRIMARY KEY,
    name        VARCHAR(200)       NOT NULL ,
    description TEXT               NOT NULL ,
    logo        BYTEA                       ,
    status      companyStatus_type NOT NULL DEFAULT 'DRAFT'::companyStatus_type
    );

-- При желании удалить таблицу,
-- комментим всё, что выше, расскоментим все что ниже:
-- DROP TABLE IF EXISTS users
