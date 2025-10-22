package Classes;

import java.time.LocalDateTime;

public class Reserva {
    private String id;
    private String espacoId;
    private String usuarioId;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Reserva(String id, String espacoId, String usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.espacoId = espacoId;
        this.usuarioId = usuarioId;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getId() { return id; }
    public String getEspacoId() { return espacoId; }
    public String getUsuarioId() { return usuarioId; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFim() { return fim; }

    public void setId(String id) { this.id = id; }
    public void setEspacoId(String espacoId) { this.espacoId = espacoId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
}