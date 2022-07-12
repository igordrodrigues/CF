package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Setor;


public interface SetorRepository extends JpaRepository <Setor, Long>{

	
	Optional<Setor> findById(Long id);
	Optional<Setor> findByAbreviatura(String abreviatura);
	Optional<Setor> findByNome(String nome);
	Optional<Setor> findByResponsavel(Funcionario responsavel);	
	boolean existsById(Long id);
	boolean existsByAbreviatura(String abreviatura);
	boolean existsByNome(String nome);
	boolean existsByResponsavel(Funcionario responsavel);

}
