package Classes;

public class Laboratorio extends EspacoFisico {
    private String tipoEquipamento;

    public Laboratorio(String id, String nome, int capacidade, String campusId, String tipoEquipamento) {
        super(id, nome, capacidade, campusId);
        this.tipoEquipamento = tipoEquipamento;
    }

    public String getTipoEquipamento() { return tipoEquipamento; }
    public void setTipoEquipamento(String tipoEquipamento) { this.tipoEquipamento = tipoEquipamento; }
}