import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import Classes.Reserva;

public class ReservaOutputStream extends OutputStream {
    private final Reserva[] reservas;
    private final int quantidade;
    private final OutputStream destino;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReservaOutputStream(Reserva[] reservas, int quantidade, OutputStream destino) {
        this.reservas = Objects.requireNonNull(reservas);
        this.quantidade = quantidade;
        this.destino = destino;
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    public void enviarReservas() throws IOException {
        for (int i = 0; i < quantidade; i++) {
            Reserva r = reservas[i];

            byte[] idBytes = r.getId().getBytes(StandardCharsets.UTF_8);
            byte[] usuarioIdBytes = r.getUsuarioId().getBytes(StandardCharsets.UTF_8);
            byte[] inicioBytes = r.getInicio().format(formatter).getBytes(StandardCharsets.UTF_8);

            writeInt(idBytes.length);
            destino.write(idBytes);

            writeInt(usuarioIdBytes.length);
            destino.write(usuarioIdBytes);

            writeInt(inicioBytes.length);
            destino.write(inicioBytes);
        }

        destino.flush();
    }

    private void writeInt(int value) throws IOException {
        destino.write((value >> 24) & 0xFF);
        destino.write((value >> 16) & 0xFF);
        destino.write((value >> 8) & 0xFF);
        destino.write(value & 0xFF);
    }

    @Override
    public void close() throws IOException {
        destino.close();
    }
}