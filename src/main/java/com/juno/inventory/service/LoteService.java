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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
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

    @Transactional
    public Lote criarLote(LoteSaveDTO dto) {
        Optional<Produto> produtoOptional = produtoRepository.findById(dto.produtoId());
        if (produtoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lote não encontrado");

        }
        Produto produto = produtoOptional.get();

        boolean loteJaExiste = loteRepository.existsByCodigoLoteAndProduto(dto.codigoLote(), produto);

        if (loteJaExiste) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lote já existente");
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

    @Transactional
    public Lote darBaixaLote(LoteSaidaDTO dto) {
        Optional<Lote> loteBuscado = loteRepository.findById(dto.idLote());

        if (loteBuscado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lote não existente");
        }

        Lote lote = loteBuscado.get();

        var quantidadeSaida = dto.quantidade();

        if (quantidadeSaida <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantidade de saída deve ser maior que zero.");
        }

        if (quantidadeSaida > lote.getQuantidadeAtual()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de saída maior que o estoque disponível.");
        }

        var quantidadeAtual = lote.getQuantidadeAtual();
        var novaQuantidade = quantidadeAtual - quantidadeSaida;

        lote.setQuantidadeAtual(novaQuantidade);

        Lote loteAtualizado = loteRepository.save(lote);

        MovimentacaoEstoque movimentacaoEstoqueSaida = new MovimentacaoEstoque();
        movimentacaoEstoqueSaida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacaoEstoqueSaida.setLote(loteAtualizado);
        movimentacaoEstoqueSaida.setData(LocalDateTime.now());
        movimentacaoEstoqueSaida.setQuantidade(quantidadeSaida);
        movimentacaoEstoqueSaida.setResponsavel(dto.responsavel());
        movimentacaoEstoqueSaida.setObservacao(dto.observacao());

        movimentacaoEstoqueRepository.save(movimentacaoEstoqueSaida);

        return loteAtualizado;
    }

    public List<Lote> listarLotes() {
        return loteRepository.findAll();
    }

    public Lote getLoteById(Long id) {
        var loteBuscado = loteRepository.findById(id);
        if (loteBuscado.isPresent()) {
            return loteBuscado.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lote não encontrado");
        }


    }

    public List<MovimentacaoEstoque> getHistoricoLoteById(Long id) {

        var loteBuscado = loteRepository.findById(id);

        if (loteBuscado.isPresent()) {

            return movimentacaoEstoqueRepository.findByLote(loteBuscado.get());

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lote não encontrado");
        }

    }
}






