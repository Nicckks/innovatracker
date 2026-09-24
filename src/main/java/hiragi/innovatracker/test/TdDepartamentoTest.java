package hiragi.innovatracker.test;

import hiragi.innovatracker.InnovatrackerApplication;
import hiragi.innovatracker.model.TdDepartamento;
import hiragi.innovatracker.repository.TdDepartamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;


public class TdDepartamentoTest {


    // 1. Recebe o repositório como parâmetro e o Scanner reutilizado
    private static void incluir(TdDepartamentoRepository rep, Scanner leitor) {
        TdDepartamento tdDepartamento = new TdDepartamento();
        System.out.println("\n--- Inclusão de Departamento ---");


        System.out.print("Sigla: ");
        tdDepartamento.setSglDepartamento(leitor.nextLine());


        System.out.print("Nome: ");
        tdDepartamento.setNmeDepartamento(leitor.nextLine());


        // 2. Salva no banco via Spring Data JPA
        rep.save(tdDepartamento);
        System.out.println("Departamento salvo com sucesso!");
    }


    private static void consultar(TdDepartamentoRepository rep, Scanner leitor) {
        System.out.println("\n--- Lista de Departamentos ---");
        rep.findAll().forEach(c ->
                System.out.printf("ID: %d | Sigla: %s | Nome: %s%n",
                        c.getIdtDepartamento(), c.getSglDepartamento(), c.getNmeDepartamento())
        );
    }


    private static void consultarNme(TdDepartamentoRepository rep, Scanner leitor) {
        System.out.println("\n--- Consultar por Nome de Departamento ---");
        System.out.println("Nome: ");
        String nome = leitor.nextLine();
        rep.findByNmeDepartamentoContainingIgnoreCaseOrderByNmeDepartamento(nome)
                .forEach(c ->
                        System.out.printf("ID: %d | Sigla: %s | Nome: %s%n",
                                c.getIdtDepartamento(), c.getSglDepartamento(), c.getNmeDepartamento())
                );
    }
    private static void excluir(TdDepartamentoRepository rep, Scanner leitor) {
        System.out.print("\nDigite o ID para excluir: ");
        Long id = leitor.nextLong();
        leitor.nextLine(); // Limpa o buffer do Enter


        if (rep.existsById(id)) {
            rep.deleteById(id);
            System.out.println("Departamento excluídO com sucesso!");
        } else {
            System.out.println("Departamento não encontradO.");
        }
    }


    public static void main(String[] args) {


        try (ConfigurableApplicationContext ctx = new SpringApplicationBuilder(InnovatrackerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args)) {


            // 3. Resgata a instância gerenciada pelo Spring
            TdDepartamentoRepository rep = ctx.getBean(TdDepartamentoRepository.class);


            // Scanner instanciado uma única vez fora do loop
            Scanner leitor = new Scanner(System.in);
            boolean sair = false;


            while (!sair) {
                System.out.println("""
                      
                       Escolha uma das opções:
                       1 - Incluir
                       2 - Consultar
                       3 - Excluir
                       4 - Consultar Nome
                       5 - Sair
                      
                       Qual a opção?
                       """);


                int opcao = leitor.nextInt();
                leitor.nextLine(); // Limpa o buffer do Enter após ler o int


                switch (opcao) {
                    case 1 -> incluir(rep, leitor);
                    case 2 -> consultar(rep, leitor);
                    case 3 -> excluir(rep, leitor);
                    case 4 -> consultarNme(rep, leitor);
                    case 5 -> sair = true;
                    default -> System.out.println("Opção inválida!");
                }
            }


            leitor.close(); // Fechado apenas ao encerrar o programa
        }
    }
}
