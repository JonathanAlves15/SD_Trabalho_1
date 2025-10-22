import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Classes.Reserva;

public class ReservaInputStream extends InputStream {
    private final InputStream origem;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ReservaInputStream(InputStream origem) {
        this.origem = origem;
    }

    @Override
    public int read() throws IOException {
        return origem.read();
    }
    
    private int readInt() throws IOException {
        int b1 = origem.read();
        int b2 = origem.read();
        int b3 = origem.read();
        int b4 = origem.read();
        if (b4 == -1) throw new IOException("Não foi possível ler inteiro do stream.");
        return ((b1 << 24) + (b2 << 16) + (b3 << 8) + b4);
    }

    private String lerString() throws IOException {
        int tamanho = readInt();
        byte[] buffer = new byte[tamanho];
        int totalLido = 0;
        while (totalLido < tamanho) {
            int lidos = origem.read(buffer, totalLido, tamanho - totalLido);
            if (lidos == -1) throw new IOException("Fim inesperado do stream.");
            totalLido += lidos;
        }
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public List<Reserva> lerReservas(int quantidade) throws IOException {
        List<Reserva> lista = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            String id = lerString();
            String usuarioId = lerString();
            String inicioStr = lerString();

            LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);

            Reserva r = new Reserva(id, "espaco-fake", usuarioId, inicio, inicio.plusHours(1));
            lista.add(r);
        }

        return lista;
    }

    @Override
    public void close() throws IOException {
        origem.close();
    }
}
