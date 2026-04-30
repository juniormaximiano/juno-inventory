package com.juno.inventory.repository;

import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findByLote(Lote lote);
}
