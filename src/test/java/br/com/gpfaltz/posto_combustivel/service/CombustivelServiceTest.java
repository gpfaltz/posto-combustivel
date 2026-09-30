package br.com.gpfaltz.posto_combustivel.service;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import br.com.gpfaltz.posto_combustivel.exception.ResourceNotFoundException;
import br.com.gpfaltz.posto_combustivel.repository.BombaRepository;
import br.com.gpfaltz.posto_combustivel.repository.CombustivelRepository;
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

class CombustivelServiceTest {

    @Mock
    private CombustivelRepository repository;

    @Mock
    private BombaRepository bombaRepository;

    @InjectMocks
    private CombustivelService service;

    private Combustivel combustivel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        combustivel = Combustivel.builder()
                .id(1L)
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("3.50"))
                .build();
    }

    @Test
    void create_ShouldPersistAndReturnResponse() {
        CombustivelRequest request = new CombustivelRequest("Gasolina", new BigDecimal("4.20"));
        when(repository.save(any(Combustivel.class))).thenAnswer(i -> {
            Combustivel c = i.getArgument(0);
            c.setId(2L);
            return c;
        });
        CombustivelResponse resp = service.create(request);
        assertEquals(2L, resp.id());
        assertEquals("Gasolina", resp.nome());
        assertEquals(new BigDecimal("4.20"), resp.precoPorLitro());
    }

    @Test
    void getAll_ShouldMapResponses() {
        when(repository.findAll()).thenReturn(Collections.singletonList(combustivel));
        var list = service.getAll();
        assertEquals(1, list.size());
        CombustivelResponse resp = list.get(0);
        assertEquals(combustivel.getId(), resp.id());
        assertEquals(combustivel.getNome(), resp.nome());
    }

    @Test
    void delete_WhenHasBomba_ShouldThrowIllegalState() {
        when(repository.existsById(1L)).thenReturn(true);
        when(bombaRepository.existsByCombustivelId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.delete(1L));
    }

    @Test
    void delete_WhenNotFound_ShouldThrowResourceNotFound() {
        when(repository.existsById(5L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(5L));
    }
}
