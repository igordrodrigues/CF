package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import projeto.CF.model.entity.Marca;

public interface MarcaRepository extends JpaRepository <Marca, Long>{
	Optional<Marca> findById(Long id);
	Optional<Marca> findByNome(String nome);
	Optional<Marca> findByAbreviatura(String abrev);
	boolean existsById(Long id);
	boolean existsByNome(String nome);

}
