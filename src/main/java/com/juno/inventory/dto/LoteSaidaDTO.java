package com.juno.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record LoteSaidaDTO(

        @NotNull
        Long idLote,

        @NotNull
        @Positive
        Integer quantidade,

        @NotBlank
        String responsavel,

        String observacao

) {}
