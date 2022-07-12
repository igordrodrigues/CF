package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import projeto.CF.model.entity.Lubrificante;

public interface LubrificanteRepository extends JpaRepository <Lubrificante, Long>{

	
	Optional<Lubrificante> findById(Long id);
	Optional<Lubrificante> findByNome(String nome);
	Optional<Lubrificante> findByDescricao(String descricao);
	boolean existsById(Long id);
	boolean existsByNome(String nome);
}
