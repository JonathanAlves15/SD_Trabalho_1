package Classes;

public abstract class EspacoFisico {
    protected String id;
    protected String nome;
    protected int capacidade;
    protected String campusId;

    public EspacoFisico(String id, String nome, int capacidade, String campusId) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.campusId = campusId;
    }
    
    public String getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidade() { return capacidade; }
    public String getCampusId() { return campusId; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
    public void setCampusId(String campusId) { this.campusId = campusId; }
}