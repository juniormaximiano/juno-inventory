package com.juno.inventory.controller;

import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.service.LoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
