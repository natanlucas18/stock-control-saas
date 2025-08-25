INSERT INTO clients (name, cnpj, enabled) VALUES ('Enrico e Elisa Buffet Ltda', '83981157000172', true);

INSERT INTO users (name, email, password, client_id, enabled) VALUES ('Nathan', 'nathan@gmail.com', '{pbkdf2}2d8e93af5a655ab01ad676fe44cffb9f1fb23dec17ff6b97b6491428bb785c1963f69959dce30e90', 1, true);
INSERT INTO users (name, email, password, client_id, enabled) VALUES ('Natan', 'natan@gmail.com', '{pbkdf2}2d8e93af5a655ab01ad676fe44cffb9f1fb23dec17ff6b97b6491428bb785c1963f69959dce30e90', 1, true);

INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO stock_locations (name, client_id) VALUES ('Cumbucas', 1);

INSERT INTO products (name, price, quantity, unit_measure, client_id, stock_location_id) VALUES ('Materia-prima', 100.80, 500, 1, 1, 1);
