package com.juno.inventory.dto;

import com.juno.inventory.model.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(
        TipoMovimentacao tipoMovimentacao,
        String codigoLote,
        int quantidade,
        LocalDateTime data,
        String responsavel,
        String observacao
) {
}




