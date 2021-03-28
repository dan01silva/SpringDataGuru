package br.com.alura.SpringData.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.SpringData.orm.Funcionario;
import br.com.alura.SpringData.orm.FuncionarioProjecao;
import br.com.alura.SpringData.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funcRepository;

	public RelatoriosService(FuncionarioRepository funcRepository) {
		this.funcRepository = funcRepository;
	}

	private boolean system = true;

	public void iniciar(Scanner entrada) {
		while (system) {
			System.out.println("Qual a ação do RELATPORIO deseja executar ?");
			System.out.println("0 - Sair ");
			System.out.println("1 - Filtro pelo Nome ");
			System.out.println("2 - Filtro pelo Nome, Salário e Data de Contração");
			System.out.println("3 - Filtro pela Data de Contração");
			System.out.println("4 - Pesquisa funcionario salário");

			int action = entrada.nextInt();

			switch (action) {
			case 1:
				buscaFuncionarioNome(entrada);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(entrada);
				break;
			case 3:
				buscaFuncionarioDataContratacao(entrada);
				break;
			case 4:
				pesquisaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar");
		String nome = scanner.next();
		List<Funcionario> list = funcRepository.findByNome(nome);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar");
		String nome = scanner.next();
		
		System.out.println("Qual data contratacao deseja pesquisar");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Qual salario deseja pesquisar");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> list = funcRepository
				.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
	private void buscaFuncionarioDataContratacao(Scanner scanner) {
		System.out.println("Qual data contratacao deseja pesquisar");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}
	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcRepository.findFuncionarioSalario();
		
		list.forEach(f -> System.out.println("Funcionario id : " + f.getId()
		+ " | nome: " + f.getNome() + " | salário: " + f.getSalario() ));
	}
}