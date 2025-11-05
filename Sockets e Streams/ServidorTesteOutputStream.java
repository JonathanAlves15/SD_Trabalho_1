import java.net.*;
import java.util.Scanner;

public class ServidorTesteOutputStream {
    public static void main(String[] args) {
        int porta = 5000;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor ouvindo na porta " + porta);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Cliente conectado.");

                Scanner scan = new Scanner(client.getInputStream());
                String clientMessage = scan.nextLine();
                System.out.println("Mensagem recebida: " + clientMessage);
                
                client.close();
                System.out.println("Conexão encerrada");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
