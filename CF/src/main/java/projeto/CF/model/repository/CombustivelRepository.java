package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Combustivel;

public interface CombustivelRepository extends JpaRepository <Combustivel, Long>{

	Optional<Combustivel> findById(Long id);
	Optional<Combustivel> findByNome(String nome);
	boolean existsById(Long id);
}
