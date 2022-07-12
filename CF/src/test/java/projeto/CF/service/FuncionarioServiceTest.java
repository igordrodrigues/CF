package projeto.CF.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.FuncionarioRepository;
import projeto.CF.model.repository.UsuarioRepository;
import projeto.CF.service.imp.FuncionarioServiceImp;
import projeto.CF.service.imp.UsuarioServiceImp;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	
	FuncionarioService service;
	FuncionarioRepository repository;
	Funcionario funcionario;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(FuncionarioRepository.class);
		service = new FuncionarioServiceImp(repository);	
	}
	
	@Before
	public void setUp2() {
		funcionario = Funcionario.builder().nome("a").chapa(1).build();
	}
	
	@Test (expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarChapaQuandoExistirChapaCadastrado() {
		//Usuario usuario = Usuario.builder().nome("a").email("a").chapa(1).build();
		repository.save(funcionario);
		
		service.validarChapa(1);
	}
	
	

}
