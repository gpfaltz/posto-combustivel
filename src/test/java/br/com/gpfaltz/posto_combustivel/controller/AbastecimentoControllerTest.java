package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.AbastecimentoResponse;
import br.com.gpfaltz.posto_combustivel.service.AbastecimentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração do {@link AbastecimentoController}.
 * Verifica os endpoints de criação, listagem, busca por ID, atualização e remoção.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=AbastecimentoControllerTest
 * </pre>
 */
@WebMvcTest(AbastecimentoController.class)
class AbastecimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbastecimentoService service;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testa o endpoint POST /api/abastecimentos.
     * Espera resposta 200 OK e campo {@code id} presente.
     */
    @Test
    void create_ShouldReturnResponse() throws Exception {
        AbastecimentoRequest request = new AbastecimentoRequest(1L, LocalDate.now(), new BigDecimal("10"));
        AbastecimentoResponse mockResponse = new AbastecimentoResponse(1L, 1L, "Bomba 1", LocalDate.now(), new BigDecimal("3.50"), new BigDecimal("10"), new BigDecimal("35.00"));
        Mockito.when(service.create(Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(post("/api/abastecimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    /**
     * Testa o endpoint GET /api/abastecimentos retornando lista não vazia.
     */
    @Test
    void getAll_ShouldReturnList() throws Exception {
        AbastecimentoResponse mockResponse = new AbastecimentoResponse(1L, 1L, "Bomba 1", LocalDate.now(), new BigDecimal("3.50"), new BigDecimal("10"), new BigDecimal("35.00"));
        Mockito.when(service.getAll()).thenReturn(Collections.singletonList(mockResponse));
        mockMvc.perform(get("/api/abastecimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    /**
     * Testa o endpoint GET /api/abastecimentos/{id} retornando o recurso solicitado.
     */
    @Test
    void getById_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        AbastecimentoResponse mockResponse = new AbastecimentoResponse(id, 1L, "Bomba 1", LocalDate.now(), new BigDecimal("3.50"), new BigDecimal("10"), new BigDecimal("35.00"));
        Mockito.when(service.getById(id)).thenReturn(mockResponse);
        mockMvc.perform(get("/api/abastecimentos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.intValue()));
        Mockito.verify(service).getById(id);
    }

    /**
     * Testa o endpoint PUT /api/abastecimentos/{id} atualizando o volume.
     */
    @Test
    void update_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        AbastecimentoRequest request = new AbastecimentoRequest(1L, LocalDate.now(), new BigDecimal("15"));
        AbastecimentoResponse mockResponse = new AbastecimentoResponse(id, 1L, "Bomba 1", LocalDate.now(), new BigDecimal("3.50"), new BigDecimal("15"), new BigDecimal("52.50"));
        Mockito.when(service.update(Mockito.eq(id), Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(put("/api/abastecimentos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volume").value(15));
        Mockito.verify(service).update(Mockito.eq(id), Mockito.any());
    }

    /**
     * Testa o endpoint DELETE /api/abastecimentos/{id} retornando 204 No Content.
     */
    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/abastecimentos/" + id))
                .andExpect(status().isNoContent());
        Mockito.verify(service).delete(id);
    }

    /**
     * Testa o endpoint GET quando não há registros, retornando array vazio.
     */
    @Test
    void getAll_EmptyList_ShouldReturnEmptyArray() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/abastecimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }
}