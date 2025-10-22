import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import votacao.VotacaoProto.*;

public class Servidor {
    private static final int PORT = 12345;
    private static final int MULTICAST_PORT = 54321;
    private static final String MULTICAST_GROUP = "230.0.0.0";

    private ServerSocket serverSocket;
    private Map<String, String> users = new HashMap<>(); // username -> password
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private Map<String, Boolean> hasVoted = new ConcurrentHashMap<>();
    private Map<Integer, Integer> votes = new ConcurrentHashMap<>();

    private volatile boolean votingOpen = true;

    public Servidor() throws IOException {
        serverSocket = new ServerSocket(PORT);

        users.put("eleitor1", "senha1");
        users.put("admin1", "adminpass");

        candidates.put(1, Candidate.newBuilder().setId(1).setName("Candidato A").build());
        candidates.put(2, Candidate.newBuilder().setId(2).setName("Candidato B").build());

        votes.put(1, 0);
        votes.put(2, 0);

        // Inicia timer para fechar votação em 2 minutos
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            votingOpen = false;
            System.out.println("Votação encerrada.");
            calcularResultado();
        }, 2, TimeUnit.MINUTES);
    }

    public void start() throws IOException {
        System.out.println("Servidor iniciado na porta " + PORT);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private void handleClient(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            LoginRequest loginReq = LoginRequest.parseDelimitedFrom(in);
            LoginResponse.Builder loginRespBuilder = LoginResponse.newBuilder();

            if (users.containsKey(loginReq.getUsername()) &&
                users.get(loginReq.getUsername()).equals(loginReq.getPassword())) {

                loginRespBuilder.setSuccess(true);

                if (loginReq.getType() == UserType.ELEITOR) {
                    loginRespBuilder.addAllCandidates(candidates.values());
                }

                loginRespBuilder.setMessage("Login realizado com sucesso.");
            } else {
                loginRespBuilder.setSuccess(false).setMessage("Credenciais inválidas.");
                loginRespBuilder.build().writeDelimitedTo(out);
                socket.close();
                return;
            }

            loginRespBuilder.build().writeDelimitedTo(out);

            if (loginReq.getType() == UserType.ELEITOR && votingOpen) {
                Vote vote = Vote.parseDelimitedFrom(in);

                if (!hasVoted.containsKey(loginReq.getUsername())) {
                    int candidateId = vote.getCandidateId();
                    if (candidates.containsKey(candidateId)) {
                        votes.put(candidateId, votes.get(candidateId) + 1);
                        hasVoted.put(loginReq.getUsername(), true);
                        System.out.println("Voto registrado: " + loginReq.getUsername() + " -> " + candidateId);
                    }
                }
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calcularResultado() {
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total de votos: " + totalVotes);
        for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
            Candidate c = candidates.get(entry.getKey());
            int count = entry.getValue();
            double percent = totalVotes == 0 ? 0 : (count * 100.0 / totalVotes);
            System.out.printf("Candidato %s: %d votos (%.2f%%)\n", c.getName(), count, percent);
        }

        int maxVotes = votes.values().stream().max(Integer::compareTo).orElse(0);
        candidates.entrySet().stream()
            .filter(e -> votes.get(e.getKey()) == maxVotes)
            .forEach(e -> System.out.println("Ganhador: " + e.getValue().getName()));
    }

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.start();
    }
}
