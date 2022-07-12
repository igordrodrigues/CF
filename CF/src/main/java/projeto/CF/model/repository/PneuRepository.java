package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Pneus;


public interface PneuRepository extends JpaRepository <Pneus, Long>{
	
	Optional<Pneus> findById(Long id);
	Optional<Pneus> findByMarca(String marca);
	Optional<Pneus> findByModelo(String modelo);
	boolean existsById(Long id);
	boolean existsByMarca(String marca);
	boolean existsByModelo(String modelo);
	
}
