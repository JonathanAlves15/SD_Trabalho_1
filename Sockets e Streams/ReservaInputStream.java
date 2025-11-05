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
        return origem.read(); // leitura simples
    }

    //Lê origem por completo e coverte em String
    public String lerOrigem() throws IOException
    {
        byte[] bytes = origem.readNBytes(origem.available());
        String texto = new String(bytes, StandardCharsets.UTF_8);
        return texto;
    }

    public List<Reserva> lerReservas() throws IOException {
        List<Reserva> reservas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String texto = lerOrigem();
        System.out.println("Origem: " + texto);

        //byte[] dados = new byte[tamanho];
        //int lidos = origem.read(dados);
        //if (lidos < tamanho) break;

        // exemplo de texto: ^Reserva{id='R1', espaco='E1', usuario='U1', inicio='2025-11-04 03:00', fim='2025-11-04 05:00'}
        String textoFormated = texto.replace("Reserva{", "")
                .replace("}", "")
                .replace("'", "")
                .replace("^", "|");

        String[] reservaString = textoFormated.split("[|]");

        for(int i = 1; i < reservaString.length; i++)
        {
            String[] partes = reservaString[i].split(", ");

            String id = partes[0].split("=")[1];
            String espaco = partes[1].split("=")[1];
            String usuario = partes[2].split("=")[1];
            String inicioStr = partes[3].split("=")[1];
            String fimStr = partes[4].split("=")[1]; 

            System.out.println(id + " | " + espaco + " | " + usuario + " | " + inicioStr + " | " + fimStr);

            LocalDateTime inicio = LocalDateTime.parse(inicioStr.trim(), formatter);
            LocalDateTime fim = LocalDateTime.parse(fimStr.trim(), formatter);

            reservas.add(new Reserva(id, espaco, usuario, inicio, fim));
        }

        return reservas;
    }

    @Override
    public void close() throws IOException {
        origem.close();
    }
}