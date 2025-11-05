import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import Classes.Reserva;

public class ReservaOutputStream extends OutputStream {

    private final Reserva[] reservas;
    private final int quantidade;
    private final OutputStream destino;

    public ReservaOutputStream(Reserva[] reservas, int quantidade, OutputStream destino) {
        this.reservas = reservas;
        this.quantidade = quantidade;
        this.destino = destino;
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b); // Escrita de um único byte
    }

    public void enviarReservas() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (int i = 0; i < quantidade && i < reservas.length; i++) {
            Reserva r = reservas[i];
            String dados = String.format(
                "Reserva{id='%s', espaco='%s', usuario='%s', inicio='%s', fim='%s'}",
                r.getId(),
                r.getEspacoId(),
                r.getUsuarioId(),
                r.getInicio().format(formatter),
                r.getFim().format(formatter)
            );

            byte[] bytes = dados.getBytes(StandardCharsets.UTF_8);
            int tamanho = bytes.length;

            // Envia o tamanho do registro
            destino.write((tamanho >> 24) & 0xFF);
            destino.write((tamanho >> 16) & 0xFF);
            destino.write((tamanho >> 8) & 0xFF);
            destino.write(tamanho & 0xFF);

            destino.write(bytes);

            destino.flush();
        }
    }

    @Override
    public void close() throws IOException {
        destino.close();
    }
}
