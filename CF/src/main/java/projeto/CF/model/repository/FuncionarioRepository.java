package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository <Funcionario, Integer>{
	Optional<Funcionario> findByChapa(Integer chapa);
	Optional<Funcionario> findByNome(String nome);
	boolean existsByChapa(Integer chapa);
	boolean existsByNome(String nome);

}
