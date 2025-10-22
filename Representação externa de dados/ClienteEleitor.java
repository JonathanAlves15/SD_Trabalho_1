import java.io.*;
import java.net.*;
import votacao.VotacaoProto.*;

public class ClienteEleitor {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // Envia login
        LoginRequest loginReq = LoginRequest.newBuilder()
                .setUsername("eleitor1")
                .setPassword("senha1")
                .setType(UserType.ELEITOR)
                .build();

        loginReq.writeDelimitedTo(out);

        // Recebe resposta login
        LoginResponse loginResp = LoginResponse.parseDelimitedFrom(in);
        if (!loginResp.getSuccess()) {
            System.out.println("Falha no login: " + loginResp.getMessage());
            socket.close();
            return;
        }

        System.out.println("Login realizado. Candidatos:");
        for (Candidate c : loginResp.getCandidatesList()) {
            System.out.println(c.getId() + " - " + c.getName());
        }

        Vote vote = Vote.newBuilder().setCandidateId(1).build();
        vote.writeDelimitedTo(out);

        System.out.println("Voto enviado.");

        socket.close();
    }
}
