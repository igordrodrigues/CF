package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.Usuario;

public interface ManutencaoRepository extends JpaRepository <Manutencao, Long>{
	Optional<Manutencao> findById(Long id);
	Optional<Manutencao> findByLocal(Local local);
	Optional<Manutencao> findByMotorista(Funcionario motorista);
	Optional<Manutencao> findByUsuario(Usuario usuario);
	boolean existsById(Long id);
}
