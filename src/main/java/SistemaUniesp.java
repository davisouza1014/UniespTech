import infra.HealthCheckServer;
import repository.AlunoRepositoryPostgres;
import service.AlunoService;

public class SistemaUniesp {

    public static void main(String[] args) throws InterruptedException {

        // Inicializa o banco
        AlunoRepositoryPostgres repository = new AlunoRepositoryPostgres();
        AlunoService service = new AlunoService(repository);

        // Inicia o Health Check HTTP na porta 8080
        HealthCheckServer.iniciar();

        System.out.println("Sistema iniciado! Health Check disponível em /health");

        // Mantém o app vivo
        Thread.currentThread().join();
    }
}