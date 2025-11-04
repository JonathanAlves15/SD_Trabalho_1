package Services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Classes.Usuario;

public abstract class UsuarioService implements Serializable {

    private static final long serialVersionUID = 1L;

    // Armazenamento temporário
    protected List<Usuario> usuarios = new ArrayList<>();

    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado: " + usuario.getNome());
    }

    public Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    public boolean remover(String id) {
        return usuarios.removeIf(u -> u.getId() == id);
    }

    public boolean editar(String id, Usuario novosDados) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario atual = usuarios.get(i);
            if (atual.getId() == id) {
                // Atualiza apenas campos que podem ser alterados
                atual.setNome(novosDados.getNome());
                System.out.println("Usuário atualizado");
                return true;
            }
        }
        return false;
    }
}