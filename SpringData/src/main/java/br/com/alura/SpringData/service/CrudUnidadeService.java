package br.com.alura.SpringData.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.SpringData.orm.UnidadeTrabalho;
import br.com.alura.SpringData.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeService {

	private UnidadeTrabalhoRepository repository;
	private boolean system = true;

	public CrudUnidadeService(UnidadeTrabalhoRepository repository) {
		this.repository = repository;
	}

	public void iniciar(Scanner entrada) {
		while (system) {	
			System.out.println("Qual a ação da Unidade deseja executar ?");
			System.out.println("0 - Sair ");
			System.out.println("1 - Salvar uma Unidade ");
			System.out.println("2 - Atualizar uma Unidade");
			System.out.println("3 - Vizualizar todas as Unidades");
			System.out.println("4 - Excluir uma Unidade");

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
		UnidadeTrabalho uniTrab = new UnidadeTrabalho();
		System.out.println("Descrição da Unidade : \n");
		uniTrab.setDescricao(entrada.next());
		System.out.println("Endereço da Unidade : \n");
		uniTrab.setEndereco(entrada.next());

		repository.save(uniTrab);
		System.out.println("Unidade Salva \n");
	}

	private void update(Scanner entrada) {
		Iterable<UnidadeTrabalho> unidades = repository.findAll();
		System.out.println("-----------LISTA DE UNIDADES: ----------\n");
		unidades.forEach((unidade) -> {
			System.out.println(unidade);
		});
		
		System.out.println("Informe o ID da unidade vc quer atualizar");
		Integer id = entrada.nextInt();
		System.out.println("Nome da Unidade :\n");
		String nome = entrada.next();

		System.out.println("Endereço da Unidade :\n");
		String endereco = entrada.nextLine();
		
		UnidadeTrabalho uniTrab = new UnidadeTrabalho();
		uniTrab.setId(id);
		uniTrab.setDescricao(nome);
		uniTrab.setEndereco(endereco);

		repository.save(uniTrab);
		System.out.println(" Unidade ATUALIZADA \n");
	}

	private void vizualizarAll() {
		Iterable<UnidadeTrabalho> unidades = repository.findAll();
		System.out.println("-----------LISTA DE CARGOS : ----------\n");
		unidades.forEach((unidade) -> {
			System.out.println(unidade);
		});
		System.out.println("\n");
	}

	private void deletarbyId(Scanner entrada) {
		Iterable<UnidadeTrabalho> unidades = repository.findAll();
		// usando LAMBDA para fazer verificação do item de entrada
		System.out.println("-----------LISTA DE CARGOS : ----------\n");
		unidades.forEach((unidade) -> {
			System.out.println(unidade);
		});
		System.out.println("Informe o id da Unidade que deseja EXCLUIR");
		repository.deleteById(entrada.nextInt());
		System.out.println(" Cargo DELETADO \n");
	}
}
