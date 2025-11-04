import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

import Classes.Reserva;

public class TesteReservaOutputStream {

    public static void main(String[] args) {
        try {
            Reserva[] reservas = new Reserva[] {
                    new Reserva("R1", "E1", "U1", LocalDateTime.now(), LocalDateTime.now().plusHours(2)),
                    new Reserva("R2", "E2", "U2", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3))
            };

            // Saída padrão
            System.out.println("=== Enviando para System.out ===");
            ReservaOutputStream out1 = new ReservaOutputStream(reservas, 2, System.out);
            out1.enviarReservas();

            // Arquivo
            System.out.println("\n\n=== Enviando para arquivo ===");
            try (FileOutputStream fos = new FileOutputStream("reservas.dat")) {
                ReservaOutputStream out2 = new ReservaOutputStream(reservas, 2, fos);
                out2.enviarReservas();
                out2.close();
            }
            

            // TCP
            System.out.println("\n\n=== Enviando via TCP ===");
            try (Socket socket = new Socket("localhost", 5000)) {
                ReservaOutputStream out3 = new ReservaOutputStream(reservas, 2, socket.getOutputStream());
                out3.enviarReservas();
                out3.close();
            } catch (IOException e) {
                System.out.println("Servidor TCP não encontrado.");
            }

            out1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
