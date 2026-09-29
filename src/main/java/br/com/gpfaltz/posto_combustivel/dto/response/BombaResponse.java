package br.com.gpfaltz.posto_combustivel.dto.response;

public class BombaResponse {
	
    private Long id;
    private String nome;
    private Long combustivelId;
    private String combustivelNome;
    
    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Long getCombustivelId() { return combustivelId; }
    public void setCombustivelId(Long combustivelId) { this.combustivelId = combustivelId; }
    public String getCombustivelNome() { return combustivelNome; }
    public void setCombustivelNome(String combustivelNome) { this.combustivelNome = combustivelNome; }
}