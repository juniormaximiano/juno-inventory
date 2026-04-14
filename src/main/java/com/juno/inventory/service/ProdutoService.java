package com.juno.inventory.service;

import com.juno.inventory.dto.ProdutoSaveDTO;
import com.juno.inventory.model.Produto;
import com.juno.inventory.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    public final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }


    public Produto save(ProdutoSaveDTO produto){
        Produto produtoSalvo = new Produto(produto);
        return produtoRepository.save(produtoSalvo);
    }

}
