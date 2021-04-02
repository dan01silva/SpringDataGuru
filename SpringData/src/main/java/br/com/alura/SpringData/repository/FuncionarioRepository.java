package br.com.alura.SpringData.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.SpringData.orm.Funcionario;
import br.com.alura.SpringData.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer> { //PagingAndSortingRepository
	//DERIVED query
	List<Funcionario> findByNome(String nome);
	//JPQL 
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
			+ "AND f.salario >= :salario AND f.contratoDate = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);
	//NativeQuery
	@Query(value = "SELECT * FROM funcionarios f WHERE  f.contrato_date >= :data",
			nativeQuery = true)//deixando explicito para o spring que isso é uma native query
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
	
	List<Funcionario> findAll(Specification<Funcionario> or);
}

	