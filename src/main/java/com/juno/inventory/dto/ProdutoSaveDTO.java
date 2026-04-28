package com.juno.inventory.dto;

import java.math.BigDecimal;

public record ProdutoSaveDTO(
        String nome,
        BigDecimal preco,
        String sku
) {}