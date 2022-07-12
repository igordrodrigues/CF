package projeto.CF.service.imp;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import projeto.CF.exception.ErroAutenticacao;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.exception.ErroCriacaoRegistroUsuario;
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.UsuarioRepository;
import projeto.CF.service.UsuarioService;


@Service
public class UsuarioServiceImp implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;

	
	public UsuarioServiceImp(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(Integer chapa, String senha) {
		Optional<Usuario> usuario = repository.findByChapa(chapa);

		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Chapa não encontrada.");
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		return usuario.get();

	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		//verifica se a chapa já esta cadastrada
		validarChapa(usuario.getChapa());
		//define erros de captação de dados da aplicação
		if(usuario.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(usuario.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(usuario.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		//cria itens para conferir campos obrigatorios não preechindos
		//apresentar todos os erros de uma vez é melhor que um a um
		boolean erro_Chapa = false, erro_Nome = false, erro_Email = false, erro_Senha = false;
		//verifica se a Chapa está vazio
		if(usuario.getChapa() == null) {erro_Chapa = true;}
		//verifica se o nome está vazio
		if(usuario.getNome().isEmpty() || usuario.getNome() == null) {erro_Nome = true;}
		//verifica se o Email está vazio
		if(usuario.getEmail().isEmpty() || usuario.getEmail() == null) {erro_Email = true;}
		//verifica se a Senha  está vazio
		if(usuario.getSenha().isEmpty() || usuario.getSenha() == null) {erro_Senha = true;}
		
		if (erro_Chapa || erro_Nome || erro_Email || erro_Senha ) {
				String msg = "";
				if(erro_Chapa) {msg.concat("Chapa obrigratorio!\n");}
				if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
				if(erro_Email) {msg.concat("Email obrigratorio!\\n");}
				if(erro_Senha) {msg.concat("Senha obrigratorio!\\n");}
				throw new ErroCriacaoRegistroUsuario(msg);
			
		}
		
		
		return repository.save(usuario);
	}

	@Override
	@Transactional
	public void deletarUsuario(Usuario usuario) {
		//verifica se a chapa já esta cadastrada
		boolean chapaValida = repository.existsByChapa(usuario.getChapa());
		if(!chapaValida) {
			throw new ErroIdentificacaoRegistro("Usuario informado não encontrado!");
			
		}
		repository.delete(usuario);
	}

	@Override
	public void validarChapa(Integer chapa) {
		boolean existe = repository.existsByChapa(chapa);
		if(existe) {
			throw new RegraNegocioException("Chapa cadastrada!");
		} 
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Email cadastrada!");
		} 
	}

	@Override
	public Usuario findById(Integer chapa) {
		Optional<Usuario> list = repository.findByChapa(chapa);
		Usuario usuario;
		if(list.isPresent()) {
			usuario = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return usuario;
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		return findById(id);
	}

	@Override
	@Transactional
	public Usuario alterarUsuario(Usuario usuario) {
		Objects.requireNonNull(usuario.getChapa());
		//define erros de captação de dados da aplicação
		if(usuario.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(usuario.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(usuario.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		//cria itens para conferir campos obrigatorios não preechindos
		//apresentar todos os erros de uma vez é melhor que um a um
		boolean erro_Chapa = false, erro_Nome = false, erro_Email = false, erro_Senha = false;
		//verifica se a Chapa está vazio
		if(usuario.getChapa() == null) {erro_Chapa = true;}
		//verifica se o nome está vazio
		if(usuario.getNome().isEmpty() || usuario.getNome() == null) {erro_Nome = true;}
		//verifica se o Email está vazio
		if(usuario.getEmail().isEmpty() || usuario.getEmail() == null) {erro_Email = true;}
		//verifica se a Senha  está vazio
		if(usuario.getSenha().isEmpty() || usuario.getSenha() == null) {erro_Senha = true;}
		
		if (erro_Chapa || erro_Nome || erro_Email || erro_Senha ) {
				String msg = "";
				if(erro_Chapa) {msg.concat("Chapa obrigratorio!\n");}
				if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
				if(erro_Email) {msg.concat("Email obrigratorio!\\n");}
				if(erro_Senha) {msg.concat("Senha obrigratorio!\\n");}
				throw new ErroCriacaoRegistroUsuario(msg);
		}
		return repository.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscarUsuario(Usuario filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void desativaUsuario(Usuario registro) {
		registro.setAtivo(false);
		salvarUsuario(registro);
	}

	@Override
	public void ativaUsuario(Usuario registro) {
		registro.setAtivo(true);
		salvarUsuario(registro);
	}
	
}
