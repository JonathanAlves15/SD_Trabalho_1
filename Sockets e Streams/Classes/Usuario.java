package Classes;

public class Usuario {
    private String id;
    private String nome;
    private String senha;
    private String tipo; // "Aluno", "Professor", "Administrador"

    public Usuario(String id, String nome, String tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public String getSenha() { return senha; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setSenha(String senha) { this.senha = senha; }
}