DELETE
FROM movimentacoes_estoque;
DELETE
FROM lotes;
DELETE
FROM produtos;




INSERT INTO produtos (id, nome, sku, preco, criado_em, editado_em)
VALUES (1, 'Notebook Dell Latitude 5420', 'NB-DELL-5420', 3899.90, NOW(6), NOW(6)),
       (2, 'Monitor LG 24 Polegadas', 'MON-LG-24', 899.90, NOW(6), NOW(6)),
       (3, 'Teclado Mecânico Redragon Kumara', 'TEC-RED-K552', 249.90, NOW(6), NOW(6)),
       (4, 'Mouse Logitech G203', 'MOU-LOG-G203', 159.90, NOW(6), NOW(6)),
       (5, 'Cabo HDMI 2.0 2 Metros', 'CAB-HDMI-2M', 39.90, NOW(6), NOW(6));




INSERT INTO lotes (id,
                   codigo_lote,
                   data_entrada,
                   localizacao,
                   observacao,
                   quantidade_inicial,
                   quantidade_atual,
                   responsavel,
                   produto_id)
VALUES (1, 'LOTE-NB-001', NOW(6), 'A1-01', 'Entrada inicial de notebooks para estoque principal', 20, 15,
        'Sistema Teste', 1),

       (2, 'LOTE-MON-001', NOW(6), 'A1-02', 'Monitores para reposição comercial', 35, 35, 'Operador QA', 2),

       (3, 'LOTE-TEC-001', NOW(6), 'B2-01', 'Teclados com alta saída prevista', 50, 28, 'Usuário Teste', 3),

       (4, 'LOTE-MOU-001', NOW(6), 'B2-02', 'Estoque regular de periféricos', 40, 8, 'Controle Estoque', 4),

       (5, 'LOTE-CAB-001', NOW(6), 'C1-01', 'Cabos HDMI de baixo custo', 100, 100, 'Administrador Seed', 5);




INSERT INTO movimentacoes_estoque (id,
                                   data,
                                   tipo_movimentacao,
                                   quantidade,
                                   responsavel,
                                   observacao,
                                   lote_id)
VALUES (1, NOW(6), 'ENTRADA', 20, 'Sistema Teste', 'Entrada inicial do lote LOTE-NB-001', 1),

       (2, NOW(6), 'SAIDA', 5, 'Sistema Teste', 'Saída para entrega interna', 1),

       (3, NOW(6), 'ENTRADA', 35, 'Operador QA', 'Entrada inicial do lote LOTE-MON-001', 2),

       (4, NOW(6), 'ENTRADA', 50, 'Usuário Teste', 'Entrada inicial do lote LOTE-TEC-001', 3),

       (5, NOW(6), 'SAIDA', 22, 'Usuário Teste', 'Saída para separação de pedidos', 3),

       (6, NOW(6), 'ENTRADA', 40, 'Controle Estoque', 'Entrada inicial do lote LOTE-MOU-001', 4),

       (7, NOW(6), 'SAIDA', 32, 'Controle Estoque', 'Baixa por venda acumulada', 4),

       (8, NOW(6), 'ENTRADA', 100, 'Administrador Seed', 'Entrada inicial do lote LOTE-CAB-001', 5);