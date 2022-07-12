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
import projeto.CF.model.entity.Lubrificante;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.LubrificanteRepository;
import projeto.CF.service.LubrificanteService;
import projeto.CF.service.UsuarioService;

@Service
public class LubrificanteServiceImp implements LubrificanteService {
	
	@Autowired
	private LubrificanteRepository repository;
	@Autowired
	private UsuarioService userServ;

	public LubrificanteServiceImp (LubrificanteRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Lubrificante salvarLubrificante(Lubrificante lubrificante) {
		//define erros de captação de dados da aplicação
		if(lubrificante.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(lubrificante.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(lubrificante.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
	 boolean erro_Nome = false, erro_Descricao = false;
	 if(lubrificante.getNome().isEmpty() || lubrificante.getNome() == null) {erro_Nome = true;}
	 if(lubrificante.getDescricao().isEmpty() || lubrificante.getDescricao() == null) {erro_Descricao = true;}
		
		if(erro_Descricao || erro_Nome ) {
			String msg = "";
			if(erro_Descricao) {msg.concat("Descricao obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
		}

		return repository.save(lubrificante);
	}

	@Override
	public void validarId(Long id) {
		Optional<Lubrificante> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Lubrificante findById(Long id) {
		Optional<Lubrificante> list = repository.findById(id);
		Lubrificante retorno;
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
	public List<Lubrificante> buscaLubrificante(Lubrificante filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaLubrificante(Lubrificante registro) {
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
	public void desativaLubrificante(Lubrificante registro) {
		registro.setAtivo(false);
		salvarLubrificante(registro);
	}

	@Override
	public void ativaLubrificante(Lubrificante registro) {
		registro.setAtivo(true);
		salvarLubrificante(registro);
	}
	
}
