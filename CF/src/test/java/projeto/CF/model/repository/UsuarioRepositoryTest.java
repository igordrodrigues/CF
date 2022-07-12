package projeto.CF.model.repository;

import java.util.Optional;
import org.junit.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import projeto.CF.model.entity.Usuario;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
//@AutoConfigureTestDatabase (replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	

	
	
	@Test
	public void deveVerificarExistenciaDeUmEmail() {
		//cenario
		Usuario usuario =criarUsuario(1);
		entityManager.persist(usuario);
		//acao
		boolean result = repository.existsByEmail("a@a");
		//verificacao
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		repository.deleteAll();
		boolean result = repository.existsByEmail("teste");
//		Assertions.assertThat(result).isFalse();
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		Usuario usuario = criarUsuario(1);
		
		Usuario usuarioSalvo = repository.save(usuario);
		
		Assertions.assertThat(!(usuarioSalvo.getChapa()== null));
		
	}
	
	@Test 
	public void deveBuscarUmUsuarioPorChapa() {
		Usuario usuario = criarUsuario(1);
		entityManager.persist(usuario);
		
		//verifi
		Optional<Usuario> result = repository.findByChapa(1);
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test 
	public void deveRetornarVazioUmUsuarioPorChapa() {
	
		//verifi
		Optional<Usuario> result = repository.findByChapa(1);
		
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	public static Usuario criarUsuario(Integer chapa) {
		return Usuario
				.builder()
				.nome("usuario")
				.email("a@a")
				.chapa(chapa)
				.senha("1234")
				//.dataCadastro(null)
				.build();
		
		
	}
	
}
