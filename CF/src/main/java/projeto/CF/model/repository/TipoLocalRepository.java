package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.TipoLocal;



public interface TipoLocalRepository extends JpaRepository <TipoLocal, Long>{
	
	
	Optional<TipoLocal> findById(Long id);
	Optional<TipoLocal> findByNome(String nome);
	boolean existsById(Long id);
	boolean existsByNome(String nome);

}
