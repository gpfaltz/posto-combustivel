package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.BombaRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.BombaResponse;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;
import br.com.gpfaltz.posto_combustivel.repository.AbastecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para {@link BombaService}.
 * Verifica criação, atualização, busca, listagem e exclusão de bombas.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=BombaServiceTest
 * </pre>
 */
class BombaServiceTest {

    @Mock
    private BombaRepository bombaRepo;

    @Mock
    private CombustivelRepository combustivelRepo;

    @Mock
    private AbastecimentoRepository abastecimentoRepo;

    @InjectMocks
    private BombaService service;

    private Combustivel combustivel;
    private Bomba bomba;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        combustivel = Combustivel.builder()
                .id(1L)
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
        bomba = Bomba.builder()
                .id(1L)
                .nome("Bomba 1")
                .combustivel(combustivel)
                .build();
    }

    /**
     * Verifica que a criação retorna a resposta correta.
     */
    @Test
    void create_ShouldReturnResponse() {
        BombaRequest request = new BombaRequest("Bomba X", 1L);
        when(combustivelRepo.findById(1L)).thenReturn(Optional.of(combustivel));
        when(bombaRepo.save(any(Bomba.class))).thenAnswer(i -> {
            Bomba b = i.getArgument(0);
            b.setId(2L);
            return b;
        });
        BombaResponse resp = service.create(request);
        assertEquals(2L, resp.id());
        assertEquals("Bomba X", resp.nome());
        assertEquals(combustivel.getId(), resp.combustivelId());
    }

    /**
     * Verifica que a criação lança exceção quando o combustível não existe.
     */
    @Test
    void create_WhenCombustivelNotFound_ShouldThrow() {
        BombaRequest request = new BombaRequest("Bomba Y", 99L);
        when(combustivelRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    /**
     * Verifica que a listagem mapeia as respostas.
     */
    @Test
    void getAll_ShouldMapResponses() {
        when(bombaRepo.findAll()).thenReturn(Collections.singletonList(bomba));
        var list = service.getAll();
        assertEquals(1, list.size());
        BombaResponse resp = list.get(0);
        assertEquals(bomba.getId(), resp.id());
        assertEquals(bomba.getNome(), resp.nome());
    }

    /**
     * Verifica que a exclusão lança exceção quando há abastecimentos associados.
     */
    @Test
    void delete_WhenHasAbastecimento_ShouldThrowIllegalState() {
        when(bombaRepo.existsById(1L)).thenReturn(true);
        when(abastecimentoRepo.existsByBombaId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.delete(1L));
    }

    /**
     * Verifica que a busca por ID retorna a resposta quando encontrada.
     */
    @Test
    void getById_WhenExists_ShouldReturnResponse() {
        when(bombaRepo.findById(1L)).thenReturn(Optional.of(bomba));
        BombaResponse resp = service.getById(1L);
        assertEquals(bomba.getId(), resp.id());
        assertEquals(bomba.getNome(), resp.nome());
        assertEquals(combustivel.getId(), resp.combustivelId());
        assertEquals(combustivel.getNome(), resp.combustivelNome());
        assertEquals(combustivel.getPrecoPorLitro(), resp.precoPorLitro());
    }

    /**
     * Verifica que a busca por ID lança exceção quando não encontrada.
     */
    @Test
    void getById_WhenNotFound_ShouldThrowException() {
        when(bombaRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    /**
     * Verifica que a atualização retorna a resposta atualizada.
     */
    @Test
    void update_ShouldReturnUpdatedResponse() {
        // Existing bomba
        Bomba existing = Bomba.builder()
                .id(1L)
                .nome("Bomba 1")
                .combustivel(combustivel)
                .build();
        // New combustivel for update
        Combustivel newComb = Combustivel.builder()
                .id(2L)
                .nome("Gasolina")
                .precoPorLitro(new BigDecimal("4.20"))
                .build();
        BombaRequest request = new BombaRequest("Bomba X", 2L);
        when(bombaRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(combustivelRepo.findById(2L)).thenReturn(Optional.of(newComb));
        when(bombaRepo.save(any(Bomba.class))).thenAnswer(i -> {
            Bomba b = i.getArgument(0);
            b.setId(1L);
            return b;
        });
        BombaResponse resp = service.update(1L, request);
        assertEquals(1L, resp.id());
        assertEquals("Bomba X", resp.nome());
        assertEquals(newComb.getId(), resp.combustivelId());
        assertEquals(newComb.getNome(), resp.combustivelNome());
        assertEquals(newComb.getPrecoPorLitro(), resp.precoPorLitro());
    }

    /**
     * Verifica que a atualização lança exceção quando a bomba não existe.
     */
    @Test
    void update_WhenBombaNotFound_ShouldThrow() {
        BombaRequest request = new BombaRequest("Bomba X", 1L);
        when(bombaRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, request));
    }

    /**
     * Verifica que a atualização lança exceção quando o combustível não existe.
     */
    @Test
    void update_WhenCombustivelNotFound_ShouldThrow() {
        BombaRequest request = new BombaRequest("Bomba X", 99L);
        when(bombaRepo.findById(1L)).thenReturn(Optional.of(bomba));
        when(combustivelRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, request));
    }

    /**
     * Verifica que a exclusão funciona quando não há abastecimentos.
     */
    @Test
    void delete_WhenExistsAndNoAbastecimento_ShouldDelete() {
        when(bombaRepo.existsById(1L)).thenReturn(true);
        when(abastecimentoRepo.existsByBombaId(1L)).thenReturn(false);
        service.delete(1L);
        verify(bombaRepo, times(1)).deleteById(1L);
    }

    /**
     * Verifica que a exclusão lança exceção quando a bomba não existe.
     */
    @Test
    void delete_WhenBombaNotFound_ShouldThrow() {
        when(bombaRepo.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }
}