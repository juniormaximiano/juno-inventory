package com.juno.inventory.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoteSaveDTO(

        @NotNull
        Long produtoId,

        @NotBlank
        String codigoLote,

        @NotNull
        @Positive
        Integer quantidadeInicial,

        @NotBlank
        String localizacao,

        @NotBlank
        String responsavel,

        String observacao

) {}
