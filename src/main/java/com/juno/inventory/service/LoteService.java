package com.juno.inventory.service;

import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import com.juno.inventory.model.Produto;
import com.juno.inventory.model.TipoMovimentacao;
import com.juno.inventory.repository.LoteRepository;
import com.juno.inventory.repository.MovimentacaoEstoqueRepository;
import com.juno.inventory.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoteService {

    private final LoteRepository loteRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public LoteService(LoteRepository loteRepository, ProdutoRepository produtoRepository, MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.loteRepository = loteRepository;
        this.produtoRepository = produtoRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }


    public Lote criarLote(LoteSaveDTO dto) {
        Optional<Produto> produtoOptional = produtoRepository.findById(dto.produtoId());
        if (produtoOptional.isEmpty()) {
            throw new RuntimeException("Produto não encontrado.");
        }
        Produto produto = produtoOptional.get();

        boolean loteJaExiste = loteRepository.existsByCodigoLoteAndProduto(dto.codigoLote(), produto);

        if (loteJaExiste) {
            throw new RuntimeException("Já existe um lote com esse código para esse produto");
        }

        Lote lote = new Lote();
        lote.setCodigoLote(dto.codigoLote());
        lote.setProduto(produto);
        lote.setQuantidadeInicial(dto.quantidadeInicial());
        lote.setQuantidadeAtual(dto.quantidadeInicial());
        lote.setLocalizacao(dto.localizacao());
        lote.setDataEntrada(LocalDateTime.now());
        lote.setResponsavel(dto.responsavel());
        lote.setObservacao(dto.observacao());
        var loteCriado = loteRepository.save(lote);

        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacaoEstoque.setLote(loteCriado);
        movimentacaoEstoque.setQuantidade(dto.quantidadeInicial());
        movimentacaoEstoque.setData(LocalDateTime.now());
        movimentacaoEstoque.setResponsavel(dto.responsavel());
        movimentacaoEstoque.setObservacao(dto.observacao());
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        return loteCriado;


    }
}
