import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

import Classes.Reserva;

public class TesteReservaInputStream {

    public static void main(String[] args) {
        try {
            // Ler da entrada padrão
            System.out.println("=== Leitura de System.in ===");
            
            Reserva[] output = new Reserva[] {
                    new Reserva("R1", "E1", "U1", LocalDateTime.now(), LocalDateTime.now().plusHours(2)),
                    new Reserva("R2", "E2", "U2", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3))
            };

            // Saída padrão
            ByteArrayOutputStream memoria = new ByteArrayOutputStream();
            ReservaOutputStream out = new ReservaOutputStream(output, output.length, memoria);
            out.enviarReservas();

            byte[] dados = memoria.toByteArray();
            ByteArrayInputStream entrada = new ByteArrayInputStream(dados);

            try (ReservaInputStream ris = new ReservaInputStream(entrada)) {
                List<Reserva> reservas = ris.lerReservas();
                for (Reserva r : reservas) {
                    System.out.println("Lido: " + r.getId() + " (" + r.getInicio() + " até " + r.getFim() + ")");
                }
            }

            out.close();

            // Ler de um arquivo
            System.out.println("\n=== Leitura de arquivo ===");
            try (FileInputStream fis = new FileInputStream("reservas.dat");
                 ReservaInputStream ris = new ReservaInputStream(fis)) {
                List<Reserva> reservas = ris.lerReservas();
                for (Reserva r : reservas) {
                    System.out.println("Lido do arquivo: " + r.getId() + " (" + r.getInicio() + " até " + r.getFim() + ")");
                }
            }

            // Ler de um servidor remoto via TCP
            System.out.println("\n=== Leitura via TCP ===");
            try (Socket socket = new Socket("localhost", 5000);
                ReservaInputStream ris = new ReservaInputStream(socket.getInputStream())) {
                List<Reserva> reservas = ris.lerReservas();
                for (Reserva r : reservas) {
                    System.out.println("Recebido via TCP: " + r.getId());
                }
            } catch (IOException e) {
                System.out.println("Servidor TCP não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
