-- Produtos para company_id = 1 (empresa de tecnologia)
INSERT INTO products (name, price, stock_max, stock_min, unit_measure, company_id)
VALUES
('Notebook Dell Inspiron', 4500.00, 10, 5, 1, 1),
('Mouse Logitech M170', 80.00, 50, 15, 1, 1),
('Teclado Mecânico Redragon', 250.00, 25, 5, 1, 1),
('Monitor LG 24"', 900.00, 10, 2, 1, 1),
('Headset HyperX Cloud', 350.00, 40, 20, 1, 1),
('Cadeira Gamer', 1200.00, 5, 2, 1, 1),
('Webcam Logitech C920', 400.00, 30, 5, 1, 1),
('HD Externo Seagate 1TB', 300.00, 40, 10, 1, 1),
('Roteador TP-Link AC1200', 150.00, 20, 5, 1, 1),
('Estabilizador SMS 2000VA', 220.00, 10, 2, 1, 1),
('Placa de Vídeo RTX 3060', 2500.00, 10, 2, 1, 1),
('Fonte de Alimentação 650W', 450.00, 20, 5, 1, 1),
('Memória RAM 16GB DDR4', 380.00, 30, 10, 1, 1),
('SSD 1TB NVMe', 600.00, 15, 3, 1, 1),
('Placa Mãe B450 Aorus', 750.00, 12, 4, 1, 1),
('Gabinete Gamer', 300.00, 10, 2, 1, 1),
('Processador AMD Ryzen 5', 950.00, 15, 5, 1, 1),
('Refrigerador de CPU', 120.00, 25, 8, 1, 1),
('Switch de Rede 8 Portas', 90.00, 40, 10, 1, 1),
('Cabo HDMI 2.0', 50.00, 60, 20, 1, 1),
('Mouse Gamer Razer', 320.00, 20, 5, 1, 1),
('Teclado Sem Fio Logitech', 180.00, 30, 10, 1, 1),
('Monitor Ultrawide LG', 1500.00, 8, 2, 1, 1),
('Webcam Full HD', 250.00, 15, 5, 1, 1),
('Caixa de Som Bluetooth', 90.00, 50, 15, 1, 1),
('Pen Drive 64GB', 45.00, 120, 30, 1, 1),
('Microfone Condensador', 280.00, 10, 3, 1, 1),
('Cabo de Rede 10m', 35.00, 60, 20, 1, 1),
('Adaptador USB-C', 60.00, 40, 10, 1, 1),
('Placa de Captura de Vídeo', 450.00, 10, 2, 1, 1);

-- Produtos para company_id = 2 (loja de roupas)
INSERT INTO products (name, price, stock_max, stock_min, unit_measure, company_id)
VALUES
('Camiseta Polo', 120.00, 40, 20, 1, 2),
('Calça Jeans Slim', 200.00, 40, 20, 1, 2);

-- Produtos para company_id = 3 (pizzaria)
INSERT INTO products (name, price, stock_max, stock_min, unit_measure, company_id)
VALUES
('Pizza Calabresa', 45.00, 10, 3, 1, 3),
('Pizza Marguerita', 42.00, 10, 3, 1, 3);
