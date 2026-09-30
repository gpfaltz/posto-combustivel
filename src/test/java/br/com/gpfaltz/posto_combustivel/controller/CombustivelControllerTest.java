package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.CombustivelResponse;
import br.com.gpfaltz.posto_combustivel.service.CombustivelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CombustivelController.class)
class CombustivelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CombustivelService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnResponse() throws Exception {
        CombustivelRequest request = new CombustivelRequest("Gasolina", new BigDecimal("4.20"));
        CombustivelResponse mockResponse = new CombustivelResponse(1L, "Gasolina", new BigDecimal("4.20"));
        Mockito.when(service.create(Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(post("/api/combustiveis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        CombustivelResponse mockResponse = new CombustivelResponse(1L, "Gasolina", new BigDecimal("4.20"));
        Mockito.when(service.getAll()).thenReturn(Collections.singletonList(mockResponse));
        mockMvc.perform(get("/api/combustiveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        CombustivelResponse mockResponse = new CombustivelResponse(id, "Gasolina", new BigDecimal("4.20"));
        Mockito.when(service.getById(id)).thenReturn(mockResponse);
        mockMvc.perform(get("/api/combustiveis/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.intValue()));
        Mockito.verify(service).getById(id);
    }

    @Test
    void update_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        CombustivelRequest request = new CombustivelRequest("Gasolina Premium", new BigDecimal("5.00"));
        CombustivelResponse mockResponse = new CombustivelResponse(id, "Gasolina Premium", new BigDecimal("5.00"));
        Mockito.when(service.update(Mockito.eq(id), Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(put("/api/combustiveis/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gasolina Premium"));
        Mockito.verify(service).update(Mockito.eq(id), Mockito.any());
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/combustiveis/" + id))
                .andExpect(status().isNoContent());
        Mockito.verify(service).delete(id);
    }

    @Test
    void getAll_EmptyList_ShouldReturnEmptyArray() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/combustiveis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}