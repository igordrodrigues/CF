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
import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.CorRepository;
import projeto.CF.service.CorService;
import projeto.CF.service.UsuarioService;
@Service
public class CorServiceImp implements CorService{

	
	@Autowired
	private CorRepository repository;
	@Autowired
	private UsuarioService userServ;

	public CorServiceImp(CorRepository repository) {
		super();
		this.repository = repository;
	} 
	
	@Override
	@Transactional
	public Cor salvarCor(Cor cor) {
		//define erros de captação de dados da aplicação
		if(cor.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(cor.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(cor.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		if(cor.getNome().isEmpty() || cor.getNome() == null) { throw new ErroCriacaoRegistroFuncionario("Nome é obrigatorio");}
		return repository.save(cor);
	}

	@Override
	public void validarId(Long id) {
		Optional<Cor> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Cor findById(Long id) {
		Optional<Cor> list = repository.findById(id);
		Cor retorno;
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
	public List<Cor> buscaCor(Cor filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaCor(Cor registro) {
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
	public void desativaCor(Cor registro) {
		registro.setAtivo(false);
		salvarCor(registro);
	}

	@Override
	public void ativaCor(Cor registro) {
		registro.setAtivo(true);
		salvarCor(registro);
	}
	
}
