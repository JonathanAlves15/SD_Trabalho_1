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

    public ReservaInputStream(InputStream origem) {
        this.origem = origem;
    }

    @Override
    public int read() throws IOException {
        return origem.read(); // leitura simples delegada
    }

    public List<Reserva> lerReservas() throws IOException {
        List<Reserva> reservas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            byte[] tamanhoBytes = new byte[4];
            int bytesLidos = origem.read(tamanhoBytes);
            if (bytesLidos == -1) break; // fim do stream
            if (bytesLidos < 4) break;

            int tamanho = ((tamanhoBytes[0] & 0xFF) << 24) |
                          ((tamanhoBytes[1] & 0xFF) << 16) |
                          ((tamanhoBytes[2] & 0xFF) << 8)  |
                          (tamanhoBytes[3] & 0xFF);

            // lê o conteúdo textual
            byte[] dados = new byte[tamanho];
            int lidos = origem.read(dados);
            if (lidos < tamanho) break;

            String texto = new String(dados, StandardCharsets.UTF_8);

            // exemplo de texto: Reserva{id='R1', espaco='E1', usuario='U1', inicio='2025-11-04 03:00', fim='2025-11-04 05:00'}
            String[] partes = texto.replace("Reserva{", "")
                    .replace("}", "")
                    .replace("'", "")
                    .split(", ");

            String id = partes[0].split("=")[1];
            String espaco = partes[1].split("=")[1];
            String usuario = partes[2].split("=")[1];
            String inicioStr = partes[3].split("=")[1];
            String fimStr = partes[4].split("=")[1];

            LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
            LocalDateTime fim = LocalDateTime.parse(fimStr, formatter);

            reservas.add(new Reserva(id, espaco, usuario, inicio, fim));
        }

        return reservas;
    }

    @Override
    public void close() throws IOException {
        origem.close();
    }
}