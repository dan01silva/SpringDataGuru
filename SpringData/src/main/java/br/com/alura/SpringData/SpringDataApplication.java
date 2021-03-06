package br.com.alura.SpringData;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.alura.SpringData.service.CrudCargoService;
import br.com.alura.SpringData.service.CrudFuncionarioService;
import br.com.alura.SpringData.service.CrudUnidadeService;
import br.com.alura.SpringData.service.RelatorioFuncionarioDinamico;
import br.com.alura.SpringData.service.RelatoriosService;

@EnableJpaRepositories
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private final CrudCargoService cargoService;
	
	private final CrudUnidadeService unidadeService;
	
	private final CrudFuncionarioService funcService;
	
	private final RelatoriosService relatoriosService;
	
	private final RelatorioFuncionarioDinamico relatorioDinamico;

	private Boolean system = true;

	public SpringDataApplication(CrudCargoService cargoService, CrudUnidadeService unidadeService,CrudFuncionarioService funcService,
			RelatoriosService relatorioService,RelatorioFuncionarioDinamico relatorioDinamico) {
		this.cargoService = cargoService;
		this.unidadeService = unidadeService;
		this.funcService = funcService;
		this.relatoriosService = relatorioService;
		this.relatorioDinamico = relatorioDinamico;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (system) {

			Scanner entrada = new Scanner(System.in);

			System.out.println("Qual ação você que executar");
			System.out.println("1 - Inicializar Cargos ");
			System.out.println("2 - Inicializar Unidades");
			System.out.println("3 - Inicializar Funcionário");
			System.out.println("4 - Gerar Relatórios");
			System.out.println("5 - Gerar Relatórios Dinâmicos");
			System.out.println("0 - Sair");

			int acao = entrada.nextInt();

			switch (acao) {
			case 1:
				cargoService.iniciar(entrada);
				break;
			case 2:
				unidadeService.iniciar(entrada);
				break;
			case 3:
				funcService.iniciar(entrada);
				break;
			case 4:
				relatoriosService.iniciar(entrada);
				break;
			case 5:
				relatorioDinamico.iniciar(entrada);
			default:
				system = false;
				System.out.println("Finalizando o Programa ");
				break;
			}
		}
	}
}