package com.juno.inventory.dto;

import java.math.BigDecimal;

public record ProdutoSaveDTO(
        String nome,
        int quantidade,
        BigDecimal preco,
        String sku
) {}