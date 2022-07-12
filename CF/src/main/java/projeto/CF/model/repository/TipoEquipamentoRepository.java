package projeto.CF.model.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.TipoEquipamento;


public interface TipoEquipamentoRepository extends JpaRepository <TipoEquipamento, Long>{
	
	Optional<TipoEquipamento> findById(Long id);
	Optional<TipoEquipamento> findByNome(String nome);
	boolean existsById(Long id);
	boolean existsByNome(String nome);

}
