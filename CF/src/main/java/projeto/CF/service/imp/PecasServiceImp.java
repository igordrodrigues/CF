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
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.model.entity.Pecas;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.PecasRepository;
import projeto.CF.service.PecasService;
import projeto.CF.service.UsuarioService;

@Service
public class PecasServiceImp implements PecasService{

	@Autowired
	private PecasRepository repository;
	@Autowired
	private UsuarioService userServ;

	public PecasServiceImp (PecasRepository repository) {
		super();
		this.repository = repository;
		
	}
	
	@Override
	@Transactional
	public Pecas salvarPecas(Pecas pecas) {
		//define erros de captação de dados da aplicação
		if(pecas.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(pecas.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(pecas.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Descricao = false;
		if(pecas.getNome().isEmpty() || pecas.getNome() == null) {erro_Nome = true;}
		if(pecas.getDescricao().isEmpty() || pecas.getDescricao() == null) {erro_Descricao = true;}

		if(erro_Descricao || erro_Nome) {
			String msg = "";
			if(erro_Descricao) {msg.concat("Descricao obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
			
		}

		return repository.save(pecas);

	}

	@Override
	public void validarId(Long id) {
		Optional<Pecas> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Pecas findById(Long id) {
		Optional<Pecas> list = repository.findById(id);
		Pecas retorno;
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
	public List<Pecas> buscaPecas(Pecas filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaPecas(Pecas registro) {
		// TODO testar se existe vinculo do registro (ErroVinculoDetectado)
		//-------------------
		//-------------------
		//-------------------
		//-------------------
		if(!(repository.existsById(registro.getId()))) {
			throw new ErroIdentificacaoRegistro("registro não encontrado!");
		}
		repository.delete(registro);
	}

	@Override
	public void desativaPecas(Pecas registro) {
		registro.setAtivo(false);
		salvarPecas(registro);
	}

	@Override
	public void ativaPecas(Pecas registro) {
		registro.setAtivo(true);
		salvarPecas(registro);
	}
	

}
