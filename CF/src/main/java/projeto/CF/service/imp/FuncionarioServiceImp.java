package projeto.CF.service.imp;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.exception.ErroCriacaoRegistroFuncionario;
import projeto.CF.exception.ErroCriacaoRegistroUsuario;
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.FuncionarioRepository;
import projeto.CF.model.repository.UsuarioRepository;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.UsuarioService;

@Service
public class FuncionarioServiceImp implements FuncionarioService{

	@Autowired
	private FuncionarioRepository repository;
	@Autowired
	private UsuarioService userServ;

	public FuncionarioServiceImp(FuncionarioRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Funcionario salvarFuncionario(Funcionario funcionario) {
		//define erros de captação de dados da aplicação
		if(funcionario.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(funcionario.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(funcionario.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		validarChapa(funcionario.getChapa());

		boolean erro_Chapa = false, erro_Nome = false;
		//verifica se a Chapa está vazio
		if(funcionario.getChapa() == null) {erro_Chapa = true;}
		//verifica se o nome está vazio
		if(funcionario.getNome().isEmpty() || funcionario.getNome() == null) {erro_Nome = true;}
		
		if (erro_Chapa || erro_Nome ) {
			String msg = "";
			if(erro_Chapa) {msg.concat("Chapa obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
		}
	
		return repository.save(funcionario);
	}



	@Override
	public void validarChapa(Integer chapa) {
		boolean existe = repository.existsByChapa(chapa);
		if(existe) {
			throw new RegraNegocioException("Chapa cadastrada!");
		} 
	}

	@Override
	public Funcionario findById(Integer chapa) {
		Optional<Funcionario> list = repository.findByChapa(chapa);
		Funcionario retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;

	}
	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<Funcionario> buscaFuncionario(Funcionario filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaFuncionario(Funcionario registro) {
		// TODO testar se existe vinculo do registro (ErroVinculoDetectado)
		//-------------------
		//-------------------
		//-------------------
		//-------------------
		if(!(repository.existsById(registro.getChapa()))) {
			throw new ErroIdentificacaoRegistro("registro não encontrado!");
		}
		repository.delete(registro);	
	}

	@Override
	public void desativaFuncionario(Funcionario registro) {
		registro.setAtivo(false);
		salvarFuncionario(registro);
	}

	@Override
	public void ativaFuncionario(Funcionario registro) {
		registro.setAtivo(true);
		salvarFuncionario(registro);
	}
	
}
