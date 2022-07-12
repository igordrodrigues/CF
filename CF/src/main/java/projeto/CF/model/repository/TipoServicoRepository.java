package projeto.CF.model.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.TipoServico;


public interface TipoServicoRepository extends JpaRepository <TipoServico, Long>{
	
	
	Optional<TipoServico> findById(Long id);
	Optional<TipoServico> findByNome(String nome);
	boolean existsById(Long id);
	boolean existsByNome(String nome);

}
