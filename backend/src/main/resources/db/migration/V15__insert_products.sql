-- Produtos para company_id = 1 (empresa de tecnologia)
INSERT INTO products (code, name, price, total_quantity, stock_max, stock_min, unit_measure, company_id)
VALUES
('PROD000001', 'Notebook Dell Inspiron', 4500.00, 0, 10, 5, 1, 1),
('PROD000002', 'Mouse Logitech M170', 80.00, 0, 50, 15, 1, 1),
('PROD000003', 'Teclado Mecânico Redragon', 250.00, 0, 25, 5, 1, 1),
('PROD000004', 'Monitor LG 24"', 900.00, 0, 10, 2, 1, 1),
('PROD000005', 'Headset HyperX Cloud', 350.00, 0, 40, 20, 1, 1),
('PROD000006', 'Cadeira Gamer', 1200.00, 0, 5, 2, 1, 1),
('PROD000007', 'Webcam Logitech C920', 400.00, 0, 30, 5, 1, 1),
('PROD000008', 'HD Externo Seagate 1TB', 300.00, 0, 40, 10, 1, 1),
('PROD000009', 'Roteador TP-Link AC1200', 150.00, 0, 20, 5, 1, 1),
('PROD000010', 'Estabilizador SMS 2000VA', 220.00, 0, 10, 2, 1, 1),
('PROD000011', 'Placa de Vídeo RTX 3060', 2500.00, 0, 10, 2, 1, 1),
('PROD000012', 'Fonte de Alimentação 650W', 450.00, 0, 20, 5, 1, 1),
('PROD000013', 'Memória RAM 16GB DDR4', 380.00, 0, 30, 10, 1, 1),
('PROD000014', 'SSD 1TB NVMe', 600.00, 0, 15, 3, 1, 1),
('PROD000015', 'Placa Mãe B450 Aorus', 750.00, 0, 12, 4, 1, 1),
('PROD000016', 'Gabinete Gamer', 300.00, 0, 10, 2, 1, 1),
('PROD000017', 'Processador AMD Ryzen 5', 950.00, 0, 15, 5, 1, 1),
('PROD000018', 'Refrigerador de CPU', 120.00, 0, 25, 8, 1, 1),
('PROD000019', 'Switch de Rede 8 Portas', 90.00, 0, 40, 10, 1, 1),
('PROD000020', 'Cabo HDMI 2.0', 50.00, 0, 60, 20, 1, 1),
('PROD000021', 'Mouse Gamer Razer', 320.00, 0, 20, 5, 1, 1),
('PROD000022', 'Teclado Sem Fio Logitech', 180.00, 0, 30, 10, 1, 1),
('PROD000023', 'Monitor Ultrawide LG', 1500.00, 0, 8, 2, 1, 1),
('PROD000024', 'Webcam Full HD', 250.00, 0, 15, 5, 1, 1),
('PROD000025', 'Caixa de Som Bluetooth', 90.00, 0, 50, 15, 1, 1),
('PROD000026', 'Pen Drive 64GB', 45.00, 0, 120, 30, 1, 1),
('PROD000027', 'Microfone Condensador', 280.00, 0, 10, 3, 1, 1),
('PROD000028', 'Cabo de Rede 10m', 35.00, 0, 60, 20, 1, 1),
('PROD000029', 'Adaptador USB-C', 60.00, 0, 40, 10, 1, 1),
('PROD000030', 'Placa de Captura de Vídeo', 450.00, 0, 10, 2, 1, 1);

-- Produtos para company_id = 2 (loja de roupas)
INSERT INTO products (code, name, price, total_quantity, stock_max, stock_min, unit_measure, company_id)
VALUES
('PROD000031', 'Camiseta Polo', 120.00, 0, 40, 20, 1, 2),
('PROD000032', 'Calça Jeans Slim', 200.00, 0, 40, 20, 1, 2);


-- Produtos para company_id = 3 (pizzaria)
INSERT INTO products (code, name, price, total_quantity, stock_max, stock_min, unit_measure, company_id)
VALUES
('PROD000033', 'Pizza Calabresa', 45.00, 0, 10, 3, 1, 3),
('PROD000034', 'Pizza Marguerita', 42.00, 0, 10, 3, 1, 3);

