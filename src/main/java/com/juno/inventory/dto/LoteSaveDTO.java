package com.juno.inventory.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record LoteSaveDTO(

        @ManyToOne
        @JoinColumn(name = "produto_id", nullable = false)
         Long produtoId,
        String codigoLote,
        int quantidadeInicial,
        String localizacao,
        String responsavel,
        String observacao

) {
}
