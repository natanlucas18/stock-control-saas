-- Produtos para company_id = 1 (empresa de tecnologia)
INSERT INTO products (name, price, quantity, stock_max, stock_min, unit_measure, company_id, stock_location_id)
VALUES ('Notebook Dell Inspiron', 4500.00, 10, 10, 5, 1, 1, 1),
        ('Mouse Logitech M170', 80.00, 50, 50, 15, 1, 1, 1),
        ('Teclado Mecânico Redragon', 250.00, 20, 25, 5, 1, 1, 1),
        ('Monitor LG 24"', 900.00, 15, 10, 2, 1, 1, 1),
        ('Headset HyperX Cloud', 350.00, 25, 40, 20, 1, 1, 1),
        ('Cadeira Gamer', 1200.00, 5, 5, 2, 1, 1, 2),
        ('Webcam Logitech C920', 400.00, 8, 30, 5, 1, 1, 2),
        ('HD Externo Seagate 1TB', 300.00, 30, 40, 10, 1, 1, 2),
        ('Roteador TP-Link AC1200', 150.00, 12, 20, 5, 1, 1, 2),
        ('Estabilizador SMS 2000VA', 220.00, 18, 10, 2, 1, 1, 2);

-- Produtos para company_id = 2 (loja de roupas)
INSERT INTO products (name, price, quantity, stock_max, stock_min, unit_measure, company_id, stock_location_id)
VALUES ('Camiseta Polo', 120.00, 40, 40, 20, 1, 2, 1),
        ('Calça Jeans Slim', 200.00, 30, 40, 20, 1, 2, 1),
        ('Tênis Nike Air Max', 600.00, 20, 20, 5, 1, 2, 1),
        ('Jaqueta de Couro', 850.00, 10, 15, 7, 1, 2, 1),
        ('Relógio Casio', 300.00, 15, 5, 2, 1, 2, 1);

-- Produtos para company_id = 3 (pizzaria)
INSERT INTO products (name, price, quantity, stock_max, stock_min, unit_measure, company_id, stock_location_id)
VALUES ('Pizza Calabresa', 45.00, 100, 10, 3, 1, 3, 1),
        ('Pizza Marguerita', 42.00, 80, 10, 3, 1, 3, 1),
        ('Refrigerante Coca-Cola 2L', 12.00, 200, 15, 5, 1, 3, 1),
        ('Suco Natural Laranja 1L', 15.00, 60, 8, 4, 1, 3, 1),
        ('Sobremesa Pudim', 18.00, 40, 5, 2, 1, 3, 1);
