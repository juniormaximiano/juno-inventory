package com.juno.inventory.controller;

import com.juno.inventory.dto.ProdutoSaveDTO;
import com.juno.inventory.model.Produto;
import com.juno.inventory.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@RequestBody @Valid ProdutoSaveDTO dto) {
        return produtoService.save(dto);
    }
}