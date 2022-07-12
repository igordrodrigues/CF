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
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.LocalRepository;
import projeto.CF.service.LocalService;
import projeto.CF.service.TipoLocalService;
import projeto.CF.service.UsuarioService;

@Service
public class LocalServiceImp implements LocalService{

	@Autowired
	private LocalRepository repository;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private TipoLocalService tlServ;
	public LocalServiceImp (LocalRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Local salvarLocal(Local local) {
		//define erros de captação de dados da aplicação
		if(local.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(local.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(local.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Tipo = false;
		if(local.getNome().isEmpty() || local.getNome() == null) {erro_Nome = true;}
		if(local.getTipo() == null) {erro_Tipo = true;}
		
		if(erro_Tipo || erro_Nome ) {
			String msg = "";
			if(erro_Tipo) {msg.concat("Tipo obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}		
			throw new ErroCriacaoRegistroFuncionario(msg);			
		}

		
		return repository.save(local);
	}

	@Override
	public void validarId(Long id) {
		Optional<Local> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Local findById(Long id) {
		Optional<Local> list = repository.findById(id);
		Local retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;

	}

	@Override
	public TipoLocal recuperaTipoLocal(Long id) {
		return tlServ.findById(id);
	}
	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<Local> buscaLocal(Local filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaLocal(Local registro) {
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
	public void desativaLocal(Local registro) {
		registro.setAtivo(false);
		salvarLocal(registro);
	}

	@Override
	public void ativaLocal(Local registro) {
		registro.setAtivo(true);
		salvarLocal(registro);
	}
	
}
