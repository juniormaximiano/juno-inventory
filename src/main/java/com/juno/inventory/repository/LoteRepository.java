package com.juno.inventory.repository;

import com.juno.inventory.model.Lote;
import com.juno.inventory.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote,Long> {

    List<Lote> getLoteByCodigoLote(String codigoLote);

    boolean existsByCodigoLoteAndProduto(String codigoLote, Produto produto);
}
