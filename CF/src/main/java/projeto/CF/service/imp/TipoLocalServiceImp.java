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
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.TipoLocalRepository;
import projeto.CF.service.TipoLocalService;
import projeto.CF.service.UsuarioService;

@Service
public class TipoLocalServiceImp implements TipoLocalService{

	@Autowired
	private TipoLocalRepository repository;
	@Autowired
	private UsuarioService userServ;

	public TipoLocalServiceImp(TipoLocalRepository repository) {
		super();
		this.repository = repository;
		
	} 
	@Override
	@Transactional
	public TipoLocal salvarTipoLocal(TipoLocal tipoLocal) {
		validarNome(tipoLocal.getNome());
		//define erros de captação de dados da aplicação
		if(tipoLocal.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(tipoLocal.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(tipoLocal.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Descricao = false;
		if(tipoLocal.getNome().isEmpty() || tipoLocal.getNome() == null) {erro_Nome = true;}
		if(tipoLocal.getDescricao().isEmpty() || tipoLocal.getDescricao() == null) {erro_Descricao = true;}
		
		if(erro_Descricao || erro_Nome) {
			String msg = "";
			if(erro_Descricao) {msg.concat("Descricao obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
			
		}

		return repository.save(tipoLocal);
	}

	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if(existe) {
			throw new RegraNegocioException("Chapa cadastrada!");
		} 
		
	}
	@Override
	public TipoLocal findById(Long id) {
		Optional<TipoLocal> list = repository.findById(id);
		 TipoLocal retorno;
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
	public List<TipoLocal> buscaTipoLocal(TipoLocal filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}
	@Override
	public void deletaTipoLocal(TipoLocal registro) {
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
	public void desativaTipoLocal(TipoLocal registro) {
		registro.setAtivo(false);
		salvarTipoLocal(registro);
	}
	@Override
	public void ativaTipoLocal(TipoLocal registro) {
		registro.setAtivo(true);
		salvarTipoLocal(registro);			
	}
	
}
