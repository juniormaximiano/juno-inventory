package com.juno.inventory.dto;

import jakarta.validation.constraints.*;

public record AjusteDTO(
        @NotNull
        Long idLote,
        @PositiveOrZero
        @NotNull
        int novaQuantidade,
        @NotBlank
        String motivo,
        @NotBlank
        String responsavel
) {
}
