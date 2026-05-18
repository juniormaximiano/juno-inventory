package com.juno.inventory;

import com.juno.inventory.dto.AjusteDTO;
import com.juno.inventory.dto.LoteOutputDTO;
import com.juno.inventory.dto.LoteSaveDTO;
import com.juno.inventory.model.Lote;
import com.juno.inventory.model.MovimentacaoEstoque;
import com.juno.inventory.model.Produto;
import com.juno.inventory.repository.LoteRepository;
import com.juno.inventory.repository.MovimentacaoEstoqueRepository;
import com.juno.inventory.repository.ProdutoRepository;
import com.juno.inventory.service.LoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoteServiceTest {

    @Mock
    private LoteRepository loteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @InjectMocks
    private LoteService loteService;

    @Test
    void naoDevePermitirSaidaMaiorQueEstoque() {
     Lote loteTeste = new Lote();
     loteTeste.setId(1L);
        loteTeste.setQuantidadeAtual(175);

        LoteOutputDTO dto = new LoteOutputDTO(1L, 200, "Responsável", "Teste Unitário");

        when(loteRepository.findById(1L)).thenReturn(Optional.of((loteTeste)));

        assertThrows(ResponseStatusException.class, () -> loteService.registrarSaidaLote(dto));

        verify(movimentacaoEstoqueRepository, never()).save(any(MovimentacaoEstoque.class));

    }

    @Test
    void  saidaValidaReduzEstoque(){
        Lote loteTeste = new Lote();
        loteTeste.setId(1L);
        loteTeste.setQuantidadeAtual(175);

        LoteOutputDTO dto = new LoteOutputDTO(1L,25, "Responsável", "Teste Unitário");

        when(loteRepository.findById(1L)).thenReturn(Optional.of((loteTeste)));

        when(loteRepository.save(any(Lote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lote resultado = loteService.registrarSaidaLote(dto);

        verify(movimentacaoEstoqueRepository, times(1)).save(any(MovimentacaoEstoque.class));


        assertEquals(150, resultado.getQuantidadeAtual());

    }

    @Test
    void ajusteAumentaEstoque(){

        Lote loteTeste = new Lote();
        loteTeste.setId(1L);
        loteTeste.setQuantidadeAtual(100);

        AjusteDTO dto = new AjusteDTO(1L, 130, "Teste Reajuste Aumentar", "Responsável");

        when(loteRepository.findById(1L))
                .thenReturn(Optional.of(loteTeste));


        when(loteRepository.save(any(Lote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lote resultado = loteService.ajustarLote(dto);

        verify(movimentacaoEstoqueRepository, times(1)).save(any(MovimentacaoEstoque.class));

        assertEquals(130, resultado.getQuantidadeAtual());

    }


    @Test
    void ajusteDiminuiEstoque(){
         Lote loteTeste = new Lote();
         loteTeste.setId(1L);
         loteTeste.setQuantidadeAtual(60);

        AjusteDTO dto = new AjusteDTO(1L, 30, "Teste Reajuste Diminuir", "Responsável");

        when(loteRepository.findById(1L))
                .thenReturn(Optional.of(loteTeste));

        when(loteRepository.save(any(Lote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lote resultado = loteService.ajustarLote(dto);

        verify(movimentacaoEstoqueRepository, times(1)).save(any(MovimentacaoEstoque.class));

        assertEquals(30, resultado.getQuantidadeAtual());
    }

    @Test
    void criarLoteComProdutoInexistente(){

        LoteSaveDTO dto = new LoteSaveDTO(
                1L,
                "LOTE-TESTE",
                100,
                "Setor A",
                "Responsável",
                "Teste unitário"
        );

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> loteService.criarLote(dto));
        verify(loteRepository, never()).save(any(Lote.class));

    }

    @Test
    void criarLoteDuplicado(){

        Produto produto = new Produto();
        produto.setId(1L);

        LoteSaveDTO dto = new LoteSaveDTO(
                1L,
                "LOTE-TESTE",
                100,
                "Setor A",
                "Responsável",
                "Teste unitário"
        );

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(loteRepository.existsByCodigoLoteAndProduto("LOTE-TESTE", produto))
                .thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> loteService.criarLote(dto));

        verify(loteRepository, never()).save(any(Lote.class));
        verify(movimentacaoEstoqueRepository, never()).save(any(MovimentacaoEstoque.class));
    }
}
