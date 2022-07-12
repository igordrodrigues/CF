package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Pecas;


public interface PecasRepository extends JpaRepository <Pecas, Long>{
	
	Optional<Pecas> findById(Long id);
	Optional<Pecas> findByNome(String nome);
	boolean existsById(Long id);
	boolean existsByNome(String nome);

}
