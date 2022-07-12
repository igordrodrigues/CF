package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Local;

public interface LocalRepository extends JpaRepository <Local, Long>{

	Optional<Local> findById(Long id);
	Optional<Local> findByNome(String nome);
	Optional<Local> findByTipo(String tipo);
	boolean existsById(Long id);

}
