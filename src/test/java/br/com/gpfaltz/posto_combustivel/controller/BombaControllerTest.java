package br.com.gpfaltz.posto_combustivel.controller;

import br.com.gpfaltz.posto_combustivel.dto.request.BombaRequest;
import br.com.gpfaltz.posto_combustivel.dto.response.BombaResponse;
import br.com.gpfaltz.posto_combustivel.service.BombaService;
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

@WebMvcTest(BombaController.class)
class BombaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BombaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnResponse() throws Exception {
        BombaRequest request = new BombaRequest("Bomba X", 1L);
        BombaResponse mockResponse = new BombaResponse(1L, "Bomba X", 1L, "Álcool", new BigDecimal("3.50"));
        Mockito.when(service.create(Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(post("/api/bombas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        BombaResponse mockResponse = new BombaResponse(1L, "Bomba X", 1L, "Álcool", new BigDecimal("3.50"));
        Mockito.when(service.getAll()).thenReturn(Collections.singletonList(mockResponse));
        mockMvc.perform(get("/api/bombas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        BombaResponse mockResponse = new BombaResponse(id, "Bomba X", 1L, "Álcool", new BigDecimal("3.50"));
        Mockito.when(service.getById(id)).thenReturn(mockResponse);
        mockMvc.perform(get("/api/bombas/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.intValue()));
        Mockito.verify(service).getById(id);
    }

    @Test
    void update_ShouldReturnResponse() throws Exception {
        Long id = 1L;
        BombaRequest request = new BombaRequest("Bomba X Updated", 1L);
        BombaResponse mockResponse = new BombaResponse(id, "Bomba X Updated", 1L, "Álcool", new BigDecimal("3.60"));
        Mockito.when(service.update(Mockito.eq(id), Mockito.any())).thenReturn(mockResponse);
        mockMvc.perform(put("/api/bombas/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Bomba X Updated"));
        Mockito.verify(service).update(Mockito.eq(id), Mockito.any());
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/bombas/" + id))
                .andExpect(status().isNoContent());
        Mockito.verify(service).delete(id);
    }

    @Test
    void getAll_EmptyList_ShouldReturnEmptyArray() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/bombas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}