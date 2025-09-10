INSERT INTO companies (name, cnpj, enabled) VALUES ('Empresa Teste 1', '83981157000172', true);
INSERT INTO companies (name, cnpj, enabled) VALUES ('Empresa Teste 2', '12345678000195', true);

INSERT INTO users (name, email, password, company_id, enabled) VALUES ('Nathan', 'nathan@gmail.com', '{pbkdf2}2d8e93af5a655ab01ad676fe44cffb9f1fb23dec17ff6b97b6491428bb785c1963f69959dce30e90', 1, true);
INSERT INTO users (name, email, password, company_id, enabled) VALUES ('Natan', 'natan@gmail.com', '{pbkdf2}2d8e93af5a655ab01ad676fe44cffb9f1fb23dec17ff6b97b6491428bb785c1963f69959dce30e90', 2, true);

INSERT INTO roles (authority) VALUES ('ROLE_ADMIN');
INSERT INTO roles (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);

INSERT INTO stock_locations (name, company_id) VALUES ('Armazem 1', 1);
INSERT INTO stock_locations (name, company_id) VALUES ('Galpão 1', 2);

INSERT INTO products (name, price, quantity, unit_measure, company_id, stock_location_id) VALUES ('Camisa P', 100.80, 500, 1, 1, 1);
INSERT INTO products (name, price, quantity, unit_measure, company_id, stock_location_id) VALUES ('Calça M', 150, 500, 1, 2, 2);

INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (0, 100, '2025-08-01', 'Entrada de produto', 1, 1, 1, 1);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (0, 50, '2025-08-05', 'Entrada inicial de estoque', 1, 1, 1, 1);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (1, 20, '2025-08-10', 'Saída para produção', 1, 1, 1, 1);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (0, 200, '2025-08-15', 'Reposição de fornecedor', 2, 2, 2, 2);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (1, 75, '2025-08-20', 'Venda de materiais', 2, 2, 2, 2);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (0, 30, '2025-08-25', 'Devolução de cliente', 1, 1, 1, 1);
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, company_id, stock_location_id) VALUES (1, 10, '2025-08-28', 'Consumo interno', 1, 1, 1, 1);
