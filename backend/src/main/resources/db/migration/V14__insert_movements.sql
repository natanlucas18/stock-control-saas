-- Movements client_id = 1
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, client_id, stock_location_id)
VALUES (0, 10, '2025-08-03', 'Entrada inicial de notebooks', 1, 1, 1, 1),
(1, 2,  '2025-08-07', 'Venda de notebooks', 1, 1, 1, 1),
(0, 50, '2025-08-04', 'Entrada de mouses', 2, 1, 1, 1),
(1, 5,  '2025-08-09', 'Saída de mouses', 2, 2, 1, 1),
(0, 20, '2025-08-05', 'Entrada de teclados', 3, 1, 1, 1),
(1, 3,  '2025-08-11', 'Saída de teclados', 3, 2, 1, 1),
(0, 15, '2025-08-06', 'Entrada de monitores', 4, 1, 1, 1),
(1, 4,  '2025-08-12', 'Venda de monitores', 4, 1, 1, 1),
(0, 25, '2025-08-08', 'Entrada de headsets', 5, 2, 1, 1),
(1, 6,  '2025-08-15', 'Saída de headsets', 5, 2, 1, 1),
(0, 30, '2025-08-10', 'Entrada de webcams', 7, 1, 1, 2),
(1, 7,  '2025-08-18', 'Venda de webcams', 7, 2, 1, 2),
(0, 40, '2025-08-11', 'Entrada de HDs externos', 8, 1, 1, 2),
(1, 8,  '2025-08-20', 'Saída de HDs externos', 8, 2, 1, 2),
(0, 12, '2025-08-12', 'Entrada de roteadores', 9, 1, 1, 2),
(1, 3,  '2025-08-22', 'Venda de roteadores', 9, 1, 1, 2),
(0, 18, '2025-08-13', 'Entrada de estabilizadores', 10, 2, 1, 2),
(1, 4,  '2025-08-25', 'Saída de estabilizadores', 10, 2, 1, 2);

-- Movements client_id = 2
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, client_id, stock_location_id)
VALUES (0, 40, '2025-08-02', 'Entrada de camisetas', 6, 3, 2, 3),
(1, 8,  '2025-08-08', 'Venda de camisetas', 6, 3, 2, 3),
(0, 30, '2025-08-03', 'Entrada de calças jeans', 7, 3, 2, 3),
(1, 5,  '2025-08-10', 'Saída de calças jeans', 7, 3, 2, 3),
(0, 20, '2025-08-05', 'Entrada de tênis', 8, 3, 2, 3),
(1, 2,  '2025-08-12', 'Venda de tênis', 8, 3, 2, 3),
(0, 10, '2025-08-06', 'Entrada de jaquetas', 9, 3, 2, 3),
(1, 1,  '2025-08-13', 'Venda de jaquetas', 9, 3, 2, 3),
(0, 15, '2025-08-07', 'Entrada de relógios', 10, 3, 2, 3),
(1, 3,  '2025-08-14', 'Venda de relógios', 10, 3, 2, 3);

-- Movements client_id = 3
INSERT INTO movements (type, quantity, moment, note, product_id, user_id, client_id, stock_location_id)
VALUES (0, 100, '2025-08-04', 'Produção de pizzas calabresa', 11, 4, 3, 4),
(1, 20,  '2025-08-10', 'Venda de pizzas calabresa', 11, 4, 3, 4),
(0, 80,  '2025-08-05', 'Produção de pizzas marguerita', 12, 4, 3, 4),
(1, 15,  '2025-08-11', 'Venda de pizzas marguerita', 12, 4, 3, 4),
(0, 200, '2025-08-06', 'Estoque inicial de Coca-Cola 2L', 13, 4, 3, 4),
(1, 50,  '2025-08-14', 'Venda de Coca-Cola 2L', 13, 4, 3, 4),
(0, 60,  '2025-08-07', 'Entrada de suco natural', 14, 4, 3, 4),
(1, 10,  '2025-08-16', 'Venda de suco natural', 14, 4, 3, 4),
(0, 40,  '2025-08-08', 'Produção de pudins', 15, 4, 3, 4),
(1, 8,   '2025-08-18', 'Venda de pudins', 15, 4, 3, 4);