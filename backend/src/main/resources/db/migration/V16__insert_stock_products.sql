INSERT INTO stock_product (product_id, stock_location_id, quantity)
VALUES
-- Notebook Dell Inspiron
(1, 1, 5.000),
(1, 2, 3.000),
(1, 3, 2.000),
-- Mouse Logitech M170
(2, 1, 20.000),
(2, 4, 15.000),
(2, 6, 15.000),
-- Teclado Mecânico Redragon
(3, 2, 10.000),
(3, 3, 5.000),
(3, 5, 5.000),
-- Monitor LG 24"
(4, 1, 8.000),
(4, 2, 5.000),
(4, 4, 2.000),
-- Headset HyperX Cloud
(5, 3, 10.000),
(5, 4, 8.000),
(5, 6, 7.000),
-- Cadeira Gamer
(6, 1, 2.000),
(6, 4, 2.000),
(6, 5, 1.000),
-- Webcam Logitech C920
(7, 2, 3.000),
(7, 3, 3.000),
(7, 6, 2.000),
-- HD Externo Seagate 1TB
(8, 1, 10.000),
(8, 2, 10.000),
(8, 5, 10.000),
-- Roteador TP-Link AC1200
(9, 3, 6.000),
(9, 4, 4.000),
(9, 6, 2.000),
-- Estabilizador SMS 2000VA
(10, 1, 8.000),
(10, 2, 8.000),
(10, 5, 2.000),
-- Placa de Vídeo RTX 3060
(11, 3, 3.000),
(11, 4, 1.000),
(11, 6, 1.000),
-- Fonte de Alimentação 650W
(12, 1, 5.000),
(12, 2, 5.000),
(12, 3, 5.000),
-- Memória RAM 16GB DDR4
(13, 4, 10.000),
(13, 5, 10.000),
(13, 6, 5.000),
-- SSD 1TB NVMe
(14, 1, 5.000),
(14, 3, 3.000),
(14, 4, 2.000),
-- Placa Mãe B450 Aorus
(15, 2, 4.000),
(15, 4, 2.000),
(15, 5, 2.000),
-- Gabinete Gamer
(16, 1, 3.000),
(16, 3, 2.000),
(16, 6, 2.000),
-- Processador AMD Ryzen 5
(17, 2, 5.000),
(17, 5, 3.000),
(17, 6, 2.000),
-- Refrigerador de CPU
(18, 3, 10.000),
(18, 4, 8.000),
(18, 5, 2.000);

UPDATE products p
SET total_quantity = COALESCE((
        SELECT SUM(quantity)
        FROM stock_product sp
        WHERE sp.product_id = p.id
    ), 0);
