import java.io.*;
import java.net.Socket;
import java.util.List;

import Classes.Reserva;

public class TesteReservaInputStream {

    public static void main(String[] args) {
        try {
            // Ler da entrada padrão
            System.out.println("=== Leitura de System.in ===");
            System.out.println("Cole ou redirecione bytes gerados pelo ReservaOutputStream, depois pressione Ctrl+D (Linux/macOS) ou Ctrl+Z (Windows).");
            try (ReservaInputStream ris = new ReservaInputStream(System.in)) {
                List<Reserva> reservas = ris.lerReservas();
                for (Reserva r : reservas) {
                    System.out.println("Lido: " + r.getId() + " (" + r.getInicio() + " até " + r.getFim() + ")");
                }
            }

            // Ler de um arquivo
            System.out.println("\n=== Leitura de arquivo ===");
            try (FileInputStream fis = new FileInputStream("reservas.dat");
                 ReservaInputStream ris = new ReservaInputStream(fis)) {
                List<Reserva> reservas = ris.lerReservas();
                for (Reserva r : reservas) {
                    System.out.println("Lido do arquivo: " + r.getId());
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
                System.out.println("Servidor TCP não encontrado (teste opcional).");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
