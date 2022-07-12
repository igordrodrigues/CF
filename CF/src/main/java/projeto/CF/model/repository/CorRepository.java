package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Cor;

public interface CorRepository extends JpaRepository <Cor, Long>{

	Optional<Cor> findById(Long id);
	Optional<Cor> findByNome(String nome);
	boolean existsById(Long id);

}
