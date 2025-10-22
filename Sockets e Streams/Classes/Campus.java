package Classes;

public class Campus {
    private String id;
    private String nome;
    private String localizacao;

    public Campus(String id, String nome, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalizacao() { return localizacao; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}