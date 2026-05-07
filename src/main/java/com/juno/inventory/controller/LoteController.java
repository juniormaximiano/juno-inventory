package com.juno.inventory.controller;

import com.juno.inventory.dto.LoteSaidaDTO;
import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import com.juno.inventory.model.TipoMovimentacao;
import com.juno.inventory.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return this.loteService.registrarSaidaLote(dto);
    }

    @GetMapping
    public List<Lote> listarLotes() {
        return this.loteService.listarLotes();
    }

    @GetMapping("/{id}")
    public Lote buscarLotePorId(@PathVariable Long id) {
        return this.loteService.buscarLotePorId(id);
    }


    @GetMapping("/{id}/movimentacoesPorTipo")
    public List<MovimentacaoEstoque> buscarMovimentacoesDoLote(
            @PathVariable Long id,
            @RequestParam(required = false) TipoMovimentacao tipo
    ) {
        return loteService.getMovimentacoesDoLote(id, tipo);
    }

    @GetMapping("/{id}/movimentacoesPorPeriodo")
    public List<MovimentacaoEstoque> buscarMovimentacoesPorPeriodo(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao
    ) {
        return loteService.getMovimentacoesFiltradasDoLote(id, tipoMovimentacao, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));
    }

}
