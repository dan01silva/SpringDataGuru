package br.com.alura.SpringData.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.SpringData.orm.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
	//DERIVED query
	List<Funcionario> findByNome(String nome);
	
	//JPQL 
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome"
			+ "AND f.salario >= :salario AND f.contratoDate = :	dataC")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate dataC);
	
	//NativeQuery
	@Query(value = "SELECT f FROM funcionarios f WHERE  f.contrato_date >= :dataC",
			nativeQuery = true)//deixando explicito para o spring que isso é uma native query
	List<Funcionario> findDataContratacaoMaior(LocalDate dataC);
	
	
}

	