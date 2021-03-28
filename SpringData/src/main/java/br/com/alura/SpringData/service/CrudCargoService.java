package br.com.alura.SpringData.service;

import java.util.Optional;
import java.util.Scanner;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.springframework.stereotype.Service;

import br.com.alura.SpringData.orm.Cargo;
import br.com.alura.SpringData.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository repository;
	private boolean system = true;
	Cargo cargo = new Cargo();

	/**
	 * @param repository Injeção de dependência Já que não podemos instanciar uma
	 *                   interface
	 * @return
	 */
	public CrudCargoService(CargoRepository repository) {
		this.repository = repository;
	}

	public void iniciar(Scanner entrada) {
		while (system) {
			System.out.println("Qual a ação de cargo deseja executar? ");
			System.out.println("0 - Sair ");
			System.out.println("1 - Salvar um novo Cargo ");
			System.out.println("2 - Atualizar um Cargo");
			System.out.println("3 - Deletar um Cargo");
			System.out.println("4 - Vizualizar todos os Cargos ");

			int action = entrada.nextInt();

			switch (action) {
			case 1:
				save(entrada);
				break;
			case 2:
				update(entrada);
				break;
			case 3:
				deletarbyId(entrada);
				break;
			case 4:
				vizualizarAll();
				break;
			default:
				system = false;
				break;
			}
		}
	}

	private void save(Scanner entrada) {
		// TODO Auto-generated method stub
		System.out.println("Descrição do cargo:\n ");
		String descricao = entrada.next();
		cargo.setDescricao(descricao);
		repository.save(cargo);
		System.out.println("Descirção SALVA!");
	}

	private void update(Scanner entrada) {
		Iterable<Cargo> findAll = repository.findAll();
		// usando LAMBDA para fazer verificação do item de entrada
		System.out.println("-----------LISTA DE CARGOS : ----------\n");
		findAll.forEach((cargos) -> {
			System.out.println(cargos);
		});
		
		System.out.println("Escolha o id para editar na lista");
		Integer idBy = entrada.nextInt();
		System.out.println("Cargo Buscado: " + buscarById(idBy) + "\n Qual a nova descrição de cargo: ");
		String novaDescricao = entrada.next();
		// Atualizando
		cargo.setId(idBy);
		cargo.setDescricao(novaDescricao);
		repository.save(cargo);
		System.out.println("Cargo SALVO!");
	}

	private String buscarById(Integer idParameter) {
		Optional<Cargo> id = repository.findById(idParameter);
		String descricaoById = id.get().getDescricao();

		return descricaoById;
	}

	private void deletarbyId(Scanner entrada) {
		Iterable<Cargo> cargos = repository.findAll();
		// usando LAMBDA para fazer verificação do item de entrada
		System.out.println("-----------LISTA DE CARGOS : ----------\n");
		cargos.forEach((cargo) -> {
			System.out.println(cargo);
		});
		System.out.println("Informe o id do cargo para EXCLUIR");
		Integer idBy = entrada.nextInt();
		repository.deleteById(idBy);
		
		System.out.println(" Cargo DELETADO \n");
		
	}

	private void vizualizarAll() {
		Iterable<Cargo> cargos = repository.findAll();
		System.out.println("-----------LISTA DE CARGOS : ----------\n");
		cargos.forEach((cargo) -> {
			System.out.println(cargo);
		});
		System.out.println("\n");
	}
}