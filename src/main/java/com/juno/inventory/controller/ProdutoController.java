package com.juno.inventory.controller;

import com.juno.inventory.dto.ProdutoSaveDTO;
import com.juno.inventory.model.Produto;
import com.juno.inventory.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Produtos", description = "Operações de cadastro de produtos")
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criarProduto(@RequestBody @Valid ProdutoSaveDTO dto) {
        return produtoService.save(dto);
    }
}