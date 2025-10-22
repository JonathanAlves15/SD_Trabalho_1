package Classes;

public class Sala extends EspacoFisico {
    private boolean temProjetor;

    public Sala(String id, String nome, int capacidade, String campusId, boolean temProjetor) {
        super(id, nome, capacidade, campusId);
        this.temProjetor = temProjetor;
    }

    public boolean isTemProjetor() { return temProjetor; }
    public void setTemProjetor(boolean temProjetor) { this.temProjetor = temProjetor; }
}