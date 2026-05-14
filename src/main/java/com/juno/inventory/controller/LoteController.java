package com.juno.inventory.controller;

import com.juno.inventory.dto.AjusteDTO;
import com.juno.inventory.dto.LoteOutputDTO;
import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.dto.MovimentacaoResponseDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import com.juno.inventory.model.TipoMovimentacao;
import com.juno.inventory.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lotes")
@Tag(name = "Lotes", description = "Operações de cadastro, movimentação e ajuste de lotes")
public class LoteController {

    final private LoteService loteService;

    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    @Operation(summary = "Cria um novo lote")
    @PostMapping
    public Lote criarLote(@RequestBody @Valid LoteSaveDTO lote) {
        return this.loteService.criarLote(lote);
    }

    @Operation(summary =  "Registra saída de estoque")
    @PostMapping("/saida")
    public Lote darBaixaLote(@RequestBody @Valid LoteOutputDTO dto) {
        return this.loteService.registrarSaidaLote(dto);
    }

    @Operation(summary = "Lista todos os lotes")
    @GetMapping
    public List<Lote> listarLotes() {
        return this.loteService.listarLotes();
    }

    @Operation(summary = "Retorna Lote por id")
    @GetMapping("/{id}")
    public Lote buscarLotePorId(@PathVariable Long id) {
        return this.loteService.buscarLotePorId(id);
    }


    @Operation(summary = "Lista movimentações do lote com filtro opcional por tipo")
    @GetMapping("/{id}/movimentacoesPorTipo")
    public List<MovimentacaoResponseDTO> buscarMovimentacoesDoLote(
            @PathVariable Long id,
            @RequestParam(required = false) TipoMovimentacao tipo
    ) {
        return loteService.getMovimentacoesDoLote(id, tipo);
    }

    @Operation(summary = "Lista movimentações do lote filtrando por período e tipo opcional")
    @GetMapping("/{id}/movimentacoesPorPeriodo")
    public List<MovimentacaoResponseDTO> buscarMovimentacoesPorPeriodo(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal,
            @RequestParam(required = false) TipoMovimentacao tipoMovimentacao
    ) {
        return loteService.getMovimentacoesFiltradasDoLote(id, tipoMovimentacao, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));
    }

    @Operation(summary = "Realiza ajuste manual de estoque")
    @PostMapping("/ajuste")
    public Lote ajustarLote(@RequestBody @Valid AjusteDTO ajusteDTO) {
        return this.loteService.ajustarLote(ajusteDTO);
    }

}
