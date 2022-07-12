package projeto.CF.service;

//import static org.junit.jupiter.api.Assertions.fail;
//import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

//import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
import org.junit.Assert;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.stereotype.Service;

import projeto.CF.exception.ErroAutenticacao;
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.UsuarioRepository;
import projeto.CF.service.imp.UsuarioServiceImp;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	UsuarioService service;
	UsuarioRepository repository;
	
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImp(repository);	
	}
	
	
//	@Test(expected = Test.None.class)
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "email@email.com";
		Integer chapa = 1;
		String senha = "1234";
		
		Usuario usuario = Usuario.builder().chapa(chapa).senha(senha).build();
		Mockito.when(repository.findByChapa(chapa)).thenReturn(Optional.of(usuario));
		
		
		Usuario result = service.autenticar(chapa,senha);
		Optional <Usuario> tes = null;
		Assertions.assertThat(false).isTrue();
		Assertions.assertThat(tes).isNotNull();
		Assertions.assertThat("The Lord of the Rings").isNotNull().startsWith("Jaspion");
//		fail("Expected exception was not thrown");
	}
	
//	@Test(expected = ErroAutenticacao.class)
	public void naoEncontradoUsuarioparaAutenticar() {
		String email = "email@email.com";
		Integer chapa = 1;
		String senha = "1234";
		
		Usuario usuario = Usuario.builder().chapa(chapa).senha(senha).build();
		Mockito.when(repository.findByChapa(Mockito.anyInt())).thenReturn(Optional.empty());
		
		
		Usuario result = service.autenticar(chapa,senha);
		
//		Assertions.assertThat(result).isNotNull();
//		assertTrue(result).isNotNull();
	}
	
//	@Test(expected = ErroAutenticacao.class)
	@Test
	public void senhaErradaDoUsuarioparaAutenticar() {
		Integer chapa = 1;
		String senha = "1234";
		
		Usuario usuario = Usuario.builder().chapa(chapa).senha(senha).build();
//		Mockito.when(repository.findByChapa(Mockito.anyInt())).thenReturn(Optional.of(usuario));
		Mockito.when(repository.findByChapa(Mockito.anyInt())).thenReturn(null);
			
		
		Usuario result = service.autenticar(chapa,"senha_errada");
		
		Assertions.assertThat(result).isNotNull();
//		assertTrue(result).isNotNull();
	}
	
	
//	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
				
		repository.deleteAll();
		service.validarEmail("a@a");	
	}
	
//	@Test (expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarChapaQuandoExistirChapaCadastrado() {
		Usuario usuario = Usuario.builder().nome("a").email("a").chapa(1).build();
		repository.save(usuario);
		
		service.validarChapa(1);
	}
}
