package br.com.alura.SpringData.service;

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

	private FuncionarioRepository funcRepository;
	private CargoRepository cargoRepository;
	private UnidadeTrabalhoRepository uniRepository;

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
		System.out.println("Nome do Funcionario : \n");
		func.setNome(entrada.next());
		System.out.println("CPF do Funcionario : \n");
		func.setCpf(entrada.next());
		System.out.println("Salário do Funcionario : \n");
		func.setSalario(entrada.nextBigDecimal());
		System.out.println("Data da contração  : \n");
		String dataContratacao = entrada.next();
		// formatando a data
		func.setContratoDate(LocalDate.parse(dataContratacao, f));

		System.out.println("Digite o cargoId");
		Integer cargoId = entrada.nextInt();

		List<UnidadeTrabalho> unidades = unidade(entrada);

		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		func.setCargo(cargo.get());
		func.setUnidadeTrabalhos(unidades);

		funcRepository.save(func);
		System.out.println("Funcionario SALVO");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner entrada) {
		Boolean isTrue = true;
		Integer unidadeId;
		List<UnidadeTrabalho> unidades = new ArrayList<UnidadeTrabalho>();

		while (isTrue) {
			System.out.println("Digite : 0 para sair");
			unidadeId = entrada.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = uniRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}
		return unidades;
	}

	private void update(Scanner entrada) {
		Iterable<Funcionario> funcionarios = funcRepository.findAll();
		System.out.println("-----------LISTA DE Funcionarios: ----------\n");
		funcionarios.forEach((func) -> {
			System.out.println(func);
		});
		System.out.println("Informe o ID do Funcionario vc quer atualizar");
		func.setId(entrada.nextInt());
		System.out.println("Nome do Funcionario : \n");
		func.setNome(entrada.next());
		System.out.println("CPF do Funcionario : \n");
		func.setCpf(entrada.next());
		System.out.println("Salário do Funcionario : \n");
		func.setSalario(entrada.nextBigDecimal());
		System.out.println("Data da contração  : \n");
		String dataContratacao = entrada.next();
		// formatando a data
		func.setContratoDate(LocalDate.parse(dataContratacao, f));

		List<UnidadeTrabalho> unidades = unidade(entrada);

		System.out.println("Digite o cargoId");
		Integer cargoId = entrada.nextInt();

		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		func.setCargo(cargo.get());
		func.setUnidadeTrabalhos(unidades);

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
}