package br.com.alura.SpringData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.SpringData.orm.Cargo;
import br.com.alura.SpringData.repository.CargoRepository;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {
	
	private final CargoRepository repository;
	/**
	 * @param repository
	 * Injeção de dependência
	 * Já que não podemos instanciar uma interface
	 */
	public SpringDataApplication(CargoRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Cargo cargo = new Cargo();
		cargo.setDescricao("Desenvolvedor de Software");
		repository.save(cargo);
	}
}