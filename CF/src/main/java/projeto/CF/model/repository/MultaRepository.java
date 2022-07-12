package projeto.CF.model.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Multa;

public interface MultaRepository extends JpaRepository <Multa, Long>{
	Optional<Multa> findById(Long id);
	Optional<Multa> findByDataCadastro(Date dataCadastro);
	Optional<Multa> findByDataMulta(Date dataMulta);
	boolean existsById(Long id);
}
