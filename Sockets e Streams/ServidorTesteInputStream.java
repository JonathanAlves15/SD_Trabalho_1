import java.io.*;
import java.net.*;

public class ServidorTesteInputStream {
    public static void main(String[] args) {
        int porta = 5000;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor ouvindo na porta " + porta);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Cliente conectado.");

                PrintWriter writer = new PrintWriter(client.getOutputStream());
                writer.println("^Reserva{id='R1', espaco='E1', usuario='U1', inicio='2025-11-04 03:00', fim='2025-11-04 05:00'}" + 
                "^Reserva{id='R2', espaco='E1', usuario='U1', inicio='2025-11-03 04:00', fim='2025-11-03 06:00'}");
                System.out.println("Mensagem enviada");
                
                client.close();
                System.out.println("Conexão encerrada");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}