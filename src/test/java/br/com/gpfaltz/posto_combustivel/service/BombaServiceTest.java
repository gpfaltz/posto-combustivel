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

    @Test
    void create_WhenCombustivelNotFound_ShouldThrow() {
        BombaRequest request = new BombaRequest("Bomba Y", 99L);
        when(combustivelRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    @Test
    void getAll_ShouldMapResponses() {
        when(bombaRepo.findAll()).thenReturn(Collections.singletonList(bomba));
        var list = service.getAll();
        assertEquals(1, list.size());
        BombaResponse resp = list.get(0);
        assertEquals(bomba.getId(), resp.id());
        assertEquals(bomba.getNome(), resp.nome());
    }

    @Test
    void delete_WhenHasAbastecimento_ShouldThrowIllegalState() {
        when(bombaRepo.existsById(1L)).thenReturn(true);
        when(abastecimentoRepo.existsByBombaId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.delete(1L));
    }
}
