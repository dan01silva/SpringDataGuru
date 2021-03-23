package br.com.alura.SpringData.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.SpringData.orm.Funcionario;
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

			int action = entrada.nextInt();

			switch (action) {
			case 1:
				findName(entrada);
				break;
			case 2:
				findNameSalarioData(entrada);
				break;
			case 3:
				findFuncionarioDataC(entrada);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void findName(Scanner entrada) {
		System.out.println("Qual nome deseja buscar: ");
		String nome = entrada.next();
		List<Funcionario> findByNome = funcRepository.findByNome(nome);
		findByNome.forEach(System.out::println);// imprimindo de uma maneira diferente
	}

	private void findNameSalarioData(Scanner entrada) {
		System.out.println("Informe o NOME: \n");
		String nome = entrada.next();

		System.out.println("Informe o SALÁRIO: \n");
		Double salario = entrada.nextDouble();

		System.out.println("Informe a DATA DE CONTRATAÇÃO: \n");
		String data = entrada.next();
		LocalDate date = LocalDate.parse(data, formatter);

//		List<Funcionario> listNomeSalarioContratacao = funcRepository.findNomeSalarioMaiorDataContratacao(nome, salario,date);
//		listNomeSalarioContratacao.forEach(System.out::println);
	}
	private void findFuncionarioDataC(Scanner entrada) {
		System.out.println("Informe a DATA DE CONTRATAÇÃO que deseja pesquisar\n");
		String data = entrada.next();
		LocalDate dateF = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcRepository.findDataContratacaoMaior(dateF);
		list.forEach(System.out::println);
	}
}