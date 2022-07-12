package projeto.CF.model.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;

public interface ModeloRepository extends JpaRepository <Modelo, Long>{
	Optional<Modelo> findById(Long id);
	Optional<Modelo> findByNome(String nome);
	Optional<Modelo> findByAbreviatura(String abrev);
	Optional<Modelo> findByAbreviatura(Marca marca);
	boolean existsById(Long id);
	boolean existsByNome(String nome);
}
