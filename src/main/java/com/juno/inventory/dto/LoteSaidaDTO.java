package com.juno.inventory.dto;

import java.time.LocalDateTime;

public record LoteSaidaDTO(
        Long idLote,
        int quantidade,
        String responsavel,
        String observacao
) {
}
