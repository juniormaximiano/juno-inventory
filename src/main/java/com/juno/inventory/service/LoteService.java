package com.juno.inventory.service;

import com.juno.inventory.dto.LoteSaidaDTO;
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
import java.util.List;
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

        MovimentacaoEstoque movimentacaoEstoqueEntrada = new MovimentacaoEstoque();
        movimentacaoEstoqueEntrada.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacaoEstoqueEntrada.setLote(loteCriado);
        movimentacaoEstoqueEntrada.setQuantidade(dto.quantidadeInicial());
        movimentacaoEstoqueEntrada.setData(LocalDateTime.now());
        movimentacaoEstoqueEntrada.setResponsavel(dto.responsavel());
        movimentacaoEstoqueEntrada.setObservacao(dto.observacao());
        movimentacaoEstoqueRepository.save(movimentacaoEstoqueEntrada);
        return loteCriado;


    }

    public Lote darBaixaLote(LoteSaidaDTO dto) {
        Optional<Lote> loteBuscado = loteRepository.findById(dto.idLote());
        Lote lote = getLote(dto, loteBuscado);

        MovimentacaoEstoque movimentacaoEstoqueSaida = new MovimentacaoEstoque();
        movimentacaoEstoqueSaida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacaoEstoqueSaida.setLote(lote);
        movimentacaoEstoqueSaida.setData(LocalDateTime.now());
        movimentacaoEstoqueSaida.setQuantidade(dto.quantidade());
        movimentacaoEstoqueSaida.setResponsavel(dto.responsavel());
        movimentacaoEstoqueSaida.setObservacao(dto.observacao());
        movimentacaoEstoqueRepository.save(movimentacaoEstoqueSaida);
        return loteRepository.save(lote);


    }

    private static Lote getLote(LoteSaidaDTO dto, Optional<Lote> loteBuscado) {
        var quantidadeSaida = dto.quantidade();

        if (loteBuscado.isEmpty()) {
            throw new RuntimeException("Lote não encontrado.");
        }

        Lote lote = loteBuscado.get();

        if (quantidadeSaida <= 0) {
            throw new RuntimeException("Quantidade de saída deve ser maior que zero.");
        }

        if (quantidadeSaida > lote.getQuantidadeAtual()) {
            throw new RuntimeException("Quantidade de saída maior que o estoque disponível.");
        }

        var quantidadeAtual = lote.getQuantidadeAtual();
        var novaQuantidade = quantidadeAtual - quantidadeSaida;

        lote.setQuantidadeAtual(novaQuantidade);
        return lote;
    }

    public List<Lote> listarLotes() {
        return loteRepository.findAll();
    }

    public Lote getLoteById(Long id) {
        var loteBuscado = loteRepository.findById(id);
        if (loteBuscado.isPresent()) {
            return loteBuscado.get();
        } else {
            throw new RuntimeException("Lote Não encontrado.");
        }


    }

    public List<MovimentacaoEstoque> getHistoricoLoteById(Long id) {

        var loteBuscado = loteRepository.findById(id);

        if (loteBuscado.isPresent()) {

            return movimentacaoEstoqueRepository.findByLote(loteBuscado.get());

        } else {
            throw new RuntimeException("Lote não encontrado.");
        }

    }
}






