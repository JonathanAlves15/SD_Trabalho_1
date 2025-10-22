package Services;

import java.util.ArrayList;
import java.util.List;
import Classes.EspacoFisico;

public class EspacoService {
    private List<EspacoFisico> espacos;

    public EspacoService() {
        this.espacos = new ArrayList<>();
    }

    public void adicionarEspaco(EspacoFisico espaco) {
        espacos.add(espaco);
    }

    public EspacoFisico buscarPorId(String id) {
        for (EspacoFisico e : espacos) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public List<EspacoFisico> listarTodos() {
        return new ArrayList<>(espacos);
    }

    public List<EspacoFisico> listarPorCampus(String campusId) {
        List<EspacoFisico> resultado = new ArrayList<>();
        for (EspacoFisico e : espacos) {
            if (e.getCampusId().equals(campusId)) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}