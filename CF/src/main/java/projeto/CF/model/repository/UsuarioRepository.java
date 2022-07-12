package projeto.CF.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Usuario;


public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{

	Optional<Usuario> findByChapa(Integer chapa);
	Optional<Usuario> findByNome(String nome);
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByChapaAndSenha(Integer chapa, String senha);
	boolean existsByChapa(Integer chapa);
	boolean existsByEmail(String email);
	
}
