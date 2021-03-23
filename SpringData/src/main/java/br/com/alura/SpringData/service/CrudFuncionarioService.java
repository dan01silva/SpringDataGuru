package br.com.alura.SpringData.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.SpringData.orm.Cargo;
import br.com.alura.SpringData.orm.Funcionario;
import br.com.alura.SpringData.orm.UnidadeTrabalho;
import br.com.alura.SpringData.repository.CargoRepository;
import br.com.alura.SpringData.repository.FuncionarioRepository;
import br.com.alura.SpringData.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private boolean system = true;
	private final DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funcRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository uniRepository;

	VerificationCpf valodationCpf = new VerificationCpf();
	Funcionario func = new Funcionario();

	public CrudFuncionarioService(FuncionarioRepository funcRepository, CargoRepository cargoRepository,
			UnidadeTrabalhoRepository uniRepository) {
		this.funcRepository = funcRepository;
		this.cargoRepository = cargoRepository;
		this.uniRepository = uniRepository;
	}

	public void iniciar(Scanner entrada) {
		while (system) {
			System.out.println("Qual a ação do Funcionario deseja executar ?");
			System.out.println("0 - Sair ");
			System.out.println("1 - Salvar novo Funcionario ");
			System.out.println("2 - Atualizar Funcionario");
			System.out.println("3 - Vizualizar todos Funcionarios");
			System.out.println("4 - Deletar Funcionario");

			int action = entrada.nextInt();

			switch (action) {
			case 1:
				save(entrada);
				break;
			case 2:
				update(entrada);
				break;
			case 3:
				vizualizarAll();
				break;
			case 4:
				deletarbyId(entrada);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void save(Scanner entrada) {
		boolean validacao = false;

		System.out.println("Nome do Funcionario :\n");
		String nome = entrada.next();

		while (validacao) {

			System.out.println("CPF do Funcionario :\n");
			String cpf = entrada.next();

			validacao = VerificationCpf.isCPF(cpf);
		}

		System.out.println("Salário do Funcionario :\n");
		Double Double = entrada.nextDouble();

		System.out.println("Digite o cargoId");
		Integer cargoId = entrada.nextInt();

		System.out.println("Data da contração :\n");
		String dataContratacao = entrada.next();

		List<UnidadeTrabalho> unidades = unidade(entrada);

		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		func.setCargo(cargo.get());
		func.setUnidadeTrabalhos(unidades);

		funcRepository.save(func);
		System.out.println("Funcionario SALVO");
	}

	private void update(Scanner entrada) {
		boolean validacao = false;
		
		Iterable<Funcionario> funcionarios = funcRepository.findAll();
		System.out.println("-----------LISTA DE Funcionarios: ----------\n");
		funcionarios.forEach((func) -> {
			System.out.println(func);
		});
		System.out.println("Informe o ID do Funcionario vc quer atualizar");
		Integer id = entrada.nextInt();

		System.out.println("Digite o nome");
		String nome = entrada.next();

		while (validacao) {

			System.out.println("CPF do Funcionario :\n");
			String cpf = entrada.next();

			System.out.println(VerificationCpf.imprimeCPF(cpf));
			String resp = entrada.next();
			if("SIM".equalsIgnoreCase(resp)) {
				
			}
			validacao = VerificationCpf.isCPF(cpf);
		}
		
		System.out.println("Digite o salario");
		BigDecimal salario = entrada.nextBigDecimal();

		System.out.println("Digite a data de contracao");
		String dataContratacao = entrada.next();

		System.out.println("Digite o cargoId");
		Integer cargoId = entrada.nextInt();

		func.setContratoDate(LocalDate.parse(dataContratacao, f));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		func.setCargo(cargo.get());

		funcRepository.save(func);
		System.out.println(" Funcionario ATUALIZADO \n");
	}

	private void vizualizarAll() {
		Iterable<Funcionario> funcionarios = funcRepository.findAll();
		System.out.println("-----------LISTA DE Funcionarios : ----------\n");
		funcionarios.forEach((func) -> {
			System.out.println(func);
		});
		System.out.println("\n");
	}

	private void deletarbyId(Scanner entrada) {
		Iterable<Funcionario> funcionarios = funcRepository.findAll();
		// usando LAMBDA para fazer verificação do item de entrada
		System.out.println("-----------LISTA DE Funcionarios : ----------\n");
		funcionarios.forEach((func) -> {
			System.out.println(func);
		});
		System.out.println("Informe o id do FUNCIONARIO que deseja EXCLUIR");

		funcRepository.deleteById(entrada.nextInt());
		System.out.println(" Funcionario DELETADO \n");
	}

	private List<UnidadeTrabalho> unidade(Scanner entrada) {
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();

		while (isTrue) {
			System.out.println("Informe a unidadeId (Para encerrar digite 0)");
			Integer unidadeId = entrada.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = uniRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}
		return unidades;
	}
}