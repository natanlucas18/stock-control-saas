INSERT INTO movements (type, quantity, moment, note, product_id, from_stock_location_id, to_stock_location_id, user_id, company_id)
VALUES 
-- Notebook Dell Inspiron
('ENTRADA', 10, '2025-08-03 09:00:00', 'Entrada inicial de notebooks', 1, NULL, 1, 1, 1),
('SAIDA', 2,  '2025-08-07 14:30:00', 'Venda de notebooks', 1, 1, NULL, 1, 1),
-- Mouse Logitech M170
('ENTRADA', 50, '2025-08-04 10:15:00', 'Entrada de mouses', 2, NULL, 1, 1, 1),
('SAIDA', 5,  '2025-08-09 11:45:00', 'Saída de mouses', 2, 1, NULL, 2, 1),
-- Teclado Mecânico Redragon
('ENTRADA', 20, '2025-08-05 13:00:00', 'Entrada de teclados', 3, NULL, 1, 1, 1),
('SAIDA', 3,  '2025-08-11 16:20:00', 'Saída de teclados', 3, 1, NULL, 2, 1),
-- Monitor LG
('ENTRADA', 15, '2025-08-06 08:30:00', 'Entrada de monitores', 4, NULL, 1, 1, 1),
('SAIDA', 4,  '2025-08-12 10:00:00', 'Venda de monitores', 4, 1, NULL, 1, 1),
-- Headset HyperX Cloud
('ENTRADA', 25, '2025-08-08 17:00:00', 'Entrada de headsets', 5, NULL, 1, 2, 1),
-- Processador AMD Ryzen 5 (local = 6)
('ENTRADA', 7, '2025-08-27 15:30:00', 'Entrada de Processador AMD Ryzen 5', 17, NULL, 6, 1, 1),
('SAIDA', 3, '2025-08-28 10:00:00', 'Saída de Processador AMD Ryzen 5', 17, 6, NULL, 2, 1),
-- Refrigerador de CPU (local = 6)
('ENTRADA', 15, '2025-08-29 11:00:00', 'Entrada de Refrigerador de CPU', 18, NULL, 6, 3, 1),
('SAIDA', 5, '2025-08-30 12:00:00', 'Saída de Refrigerador de CPU', 18, 6, NULL, 1, 1),
-- Switch de Rede 8 Portas (local = 1)
('ENTRADA', 20, '2025-08-31 14:00:00', 'Entrada de Switch de Rede 8 Portas', 19, NULL, 1, 2, 1),
('SAIDA', 8, '2025-09-01 09:00:00', 'Venda de Switch de Rede 8 Portas', 19, 1, NULL, 3, 1),
-- Cabo HDMI 2.0 (local = 2)
('ENTRADA', 30, '2025-09-02 10:30:00', 'Entrada de Cabo HDMI 2.0', 20, NULL, 2, 1, 1),
('SAIDA', 15, '2025-09-03 15:00:00', 'Saída de Cabo HDMI 2.0', 20, 2, NULL, 2, 1),
-- Mouse Gamer Razer (local = 1)
('ENTRADA', 10, '2025-09-04 11:00:00', 'Entrada de Mouse Gamer Razer', 21, NULL, 1, 3, 1),
('SAIDA', 4, '2025-09-05 13:00:00', 'Venda de Mouse Gamer Razer', 21, 1, NULL, 1, 1),
-- Teclado Sem Fio Logitech (local = 2)
('ENTRADA', 15, '2025-09-06 10:00:00', 'Entrada de Teclado Sem Fio Logitech', 22, NULL, 2, 2, 1),
('SAIDA', 5, '2025-09-07 14:00:00', 'Saída de Teclado Sem Fio Logitech', 22, 2, NULL, 3, 1),
-- Monitor Ultrawide LG (local = 3)
('ENTRADA', 4, '2025-09-08 09:00:00', 'Entrada de Monitor Ultrawide LG', 23, NULL, 3, 1, 1);
