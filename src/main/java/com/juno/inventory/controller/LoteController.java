package com.juno.inventory.controller;

import com.juno.inventory.dto.LoteSaidaDTO;
import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import com.juno.inventory.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    final private LoteService loteService;
    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @PostMapping
    public Lote criarLote(@RequestBody @Valid LoteSaveDTO lote) {
        return this.loteService.criarLote(lote);
    }

    @PostMapping("/saida")
    public Lote darBaixaLote(@RequestBody @Valid LoteSaidaDTO dto) {
        return this.loteService.darBaixaLote(dto);
    }

    @GetMapping
    public List<Lote> listarLotes() {
        return this.loteService.listarLotes();
    }

    @GetMapping("/{id}")
    public Lote buscarLotePorId(@PathVariable Long id) {
        return this.loteService.getLoteById(id);
    }

    @GetMapping("/{id}/movimentacoes")
    public List<MovimentacaoEstoque>buscarHistoricoLote(Long id){
        return this.loteService.getHistoricoLoteById(id);
    }

}
