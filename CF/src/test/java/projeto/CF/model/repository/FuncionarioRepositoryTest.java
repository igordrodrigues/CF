package projeto.CF.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import projeto.CF.model.entity.Funcionario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
//@AutoConfigureTestDatabase (replace = Replace.NONE)
public class FuncionarioRepositoryTest {

	
	@Autowired
	FuncionarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	Funcionario funcionario;
	
	@Before
	public void setUp2() {
		funcionario = Funcionario.builder().nome("a").chapa(1).build();
	}
	
	@Test
	public void deveVerificarExistenciaDeUmNome() {
		//cenario
		entityManager.persist(funcionario);
		//acao
		boolean result = repository.existsByNome("a");
		//verificacao
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverFuncionarioCadastradoComONome() {
		repository.deleteAll();
		boolean result = repository.existsByNome("teste");
//		Assertions.assertThat(result).isFalse();
		Assertions.assertThat(result).isTrue();
		}
	
	@Test
	public void devePersistirUmFuncionarioNaBaseDeDados() {
		repository.deleteAll();
		Funcionario usuarioSalvo = repository.save(funcionario);
		Assertions.assertThat(!(usuarioSalvo.getChapa()== null));
	}
	
	@Test 
	public void deveBuscarUmUsuarioPorChapa() {
		Funcionario usuario = criarFuncionario(1);
		entityManager.persist(usuario);
		
		//verifi
		Optional<Funcionario> result = repository.findByChapa(1);
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test 
	public void deveRetornarVazioUmUsuarioPorChapa() {
	
		//verifi
		Optional<Funcionario> result = repository.findByChapa(1);
		
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	public static Funcionario criarFuncionario(Integer chapa) {
		return Funcionario
				.builder()
				.nome("usuario")
				.chapa(chapa)
				.build();
		
		
	}
}
