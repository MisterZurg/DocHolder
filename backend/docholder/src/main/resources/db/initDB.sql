-- Разрешаем Postgres генерировать UUID
--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE EXTENSION IF NOT EXISTS pgcrypto;
SELECT gen_random_uuid();

CREATE TABLE IF NOT EXISTS users

(
    -- uid    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    uid  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name  VARCHAR(200) NOT NULL ,
    surname  VARCHAR(200) NOT NULL ,
    email VARCHAR(254) NOT NULL ,
    phone VARCHAR(20)  NOT NULL ,
    companyID BIGSERIAL NOT NULL
);
-- Не надо комментитить
-- CREATE SEQUENCE users_uid_seq START WITH _ INCREMENT BY 1;


-- Комментарий :3
-- При желании удалить таблицу,
-- комментим всё, что выше, расскоментим все что ниже:
-- DROP TABLE IF EXISTS users
-- DROP SEQUENCE IF EXISTS clients_uid_seq