package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.AbastecimentoResponse;
import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.AbastecimentoRepository;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AbastecimentoServiceTest {

    @Mock
    private AbastecimentoRepository abastecimentoRepo;

    @Mock
    private BombaRepository bombaRepo;

    @InjectMocks
    private AbastecimentoService service;

    private Bomba bomba;
    private Combustivel combustivel;

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
    void create_ShouldCalculateTotalAndReturnResponse() {
        AbastecimentoRequest request = new AbastecimentoRequest(1L, LocalDate.now(), new BigDecimal("10"));
        when(bombaRepo.findById(1L)).thenReturn(Optional.of(bomba));
        when(abastecimentoRepo.save(any(Abastecimento.class))).thenAnswer(i -> {
            Abastecimento a = i.getArgument(0);
            a.setId(1L);
            return a;
        });

        AbastecimentoResponse response = service.create(request);
        assertEquals(1L, response.id());
        assertEquals(bomba.getId(), response.bombaId());
        assertEquals(bomba.getNome(), response.bombaNome());
        assertEquals(combustivel.getPrecoPorLitro(), response.precoPorLitro());
        assertEquals(new BigDecimal("35.00"), response.valorTotal());
    }

    @Test
    void create_WhenBombaNotFound_ShouldThrowException() {
        AbastecimentoRequest request = new AbastecimentoRequest(99L, LocalDate.now(), new BigDecimal("5"));
        when(bombaRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    @Test
    void getAll_ShouldReturnMappedResponses() {
        Abastecimento abastecimento = Abastecimento.builder()
                .id(1L)
                .bomba(bomba)
                .data(LocalDate.now())
                .volume(new BigDecimal("10"))
                .valorTotal(new BigDecimal("35.00"))
                .build();
        when(abastecimentoRepo.findAll()).thenReturn(Collections.singletonList(abastecimento));
        var list = service.getAll();
        assertEquals(1, list.size());
        AbastecimentoResponse resp = list.get(0);
        assertEquals(1L, resp.id());
        assertEquals(bomba.getId(), resp.bombaId());
    }

    @Test
    void getById_WhenExists_ShouldReturnResponse() {
        Abastecimento abastecimento = Abastecimento.builder()
                .id(2L)
                .bomba(bomba)
                .data(LocalDate.now())
                .volume(new BigDecimal("8"))
                .valorTotal(new BigDecimal("28.00"))
                .build();
        when(abastecimentoRepo.findById(2L)).thenReturn(Optional.of(abastecimento));
        AbastecimentoResponse resp = service.getById(2L);
        assertEquals(2L, resp.id());
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        when(abastecimentoRepo.existsById(5L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(5L));
    }
}
