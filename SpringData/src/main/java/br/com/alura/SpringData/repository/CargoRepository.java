package br.com.alura.SpringData.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.SpringData.orm.Cargo;


@Repository
//é necessário passar esses dois parametros : Tipo da classe crud e tipo do id
public interface CargoRepository extends CrudRepository<Cargo,Integer>{

}
