import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int porta = 12345;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor ouvindo na porta " + porta);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado.");

                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());

                Mensagem request = (Mensagem) entrada.readObject();
                System.out.println("Requisição recebida: " + request.getOperacao());

                Mensagem reply = processarRequisicao(request);

                saida.writeObject(reply);

                socket.close();
                System.out.println("Conexão encerrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Mensagem processarRequisicao(Mensagem msg) {
        String operacao = msg.getOperacao();
        String parametro = msg.getParametro();
        String resposta = "";

        if ("UPPER".equalsIgnoreCase(operacao)) {
            resposta = parametro.toUpperCase();
        } else if ("LOWER".equalsIgnoreCase(operacao)) {
            resposta = parametro.toLowerCase();
        } else {
            resposta = "Operação desconhecida: " + operacao;
        }

        msg.setResposta(resposta);
        return msg;
    }
}
