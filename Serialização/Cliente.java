import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 12345;

        try (
            Socket socket = new Socket(host, porta);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.print("Digite a operação (UPPER/LOWER): ");
            String operacao = scanner.nextLine();

            System.out.print("Digite o texto: ");
            String parametro = scanner.nextLine();

            Mensagem request = new Mensagem(operacao, parametro);
            saida.writeObject(request);

            Mensagem resposta = (Mensagem) entrada.readObject();
            System.out.println("Resposta do servidor: " + resposta.getResposta());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
