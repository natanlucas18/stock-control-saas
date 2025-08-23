INSERT INTO clients (name, cnpj, enabled) VALUES ('Client A', '12345678000195', true);

INSERT INTO users (name, email, password, client_id, enabled) VALUES ('Nathan', 'nathan@gmail.com', '{pbkdf2}2d8e93af5a655ab01ad676fe44cffb9f1fb23dec17ff6b97b6491428bb785c1963f69959dce30e90', 1, true);

INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);