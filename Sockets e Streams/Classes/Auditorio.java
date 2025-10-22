package Classes;

public class Auditorio extends EspacoFisico {
    private boolean temPalco;

    public Auditorio(String id, String nome, int capacidade, String campusId, boolean temPalco) {
        super(id, nome, capacidade, campusId);
        this.temPalco = temPalco;
    }

    public boolean isTemPalco() { return temPalco; }
    public void setTemPalco(boolean temPalco) { this.temPalco = temPalco; }
}