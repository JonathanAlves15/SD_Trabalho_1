package Services;

import java.util.ArrayList;
import java.util.List;
import Classes.Reserva;

public class ReservaService {
    private List<Reserva> reservas;

    public ReservaService() {
        this.reservas = new ArrayList<>();
    }

    public boolean criarReserva(Reserva nova) {
        for (Reserva r : reservas) {
            if (r.getEspacoId().equals(nova.getEspacoId()) &&
                !(nova.getFim().isBefore(r.getInicio()) || nova.getInicio().isAfter(r.getFim()))) {
                return false;
            }
        }
        reservas.add(nova);
        return true;
    }

    public List<Reserva> listarReservasPorEspaco(String espacoId) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEspacoId().equals(espacoId)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public List<Reserva> getTodasReservas() {
        return reservas;
    }
}