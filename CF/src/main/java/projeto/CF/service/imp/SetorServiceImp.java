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
import projeto.CF.exception.ErroCriacaoRegistroUsuario;
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.SetorRepository;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.SetorService;
import projeto.CF.service.UsuarioService;

@Service
public class SetorServiceImp implements SetorService{

	@Autowired
	private SetorRepository repository;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private FuncionarioService funcServ;
	public SetorServiceImp (SetorRepository repository) {
		super();
		this.repository = repository;
	}
	@Override
	@Transactional
	public Setor salvarSetor(Setor setor) {
		validarNome(setor.getNome());
		//define erros de captação de dados da aplicação
		if(setor.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(setor.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(setor.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Abrv = false, erro_Responsavel = false;

		if(setor.getNome().isEmpty() || setor.getNome() == null) {	erro_Nome = true;	}
		if(setor.getAbreviatura().isEmpty() || setor.getAbreviatura() == null) {	erro_Abrv =true;	}
		if(setor.getResponsavel() == null) { erro_Responsavel = true;	}

		if(erro_Nome ||erro_Abrv ||erro_Responsavel) {
			String msg = "";
			if(erro_Nome ) {msg.concat("Nome obrigratorio!\n");}
			if(erro_Abrv) {msg.concat("Abreviatura obrigratorio!\n");}
			if(erro_Responsavel ) {msg.concat("Responsavel obrigratorio!\n");}
			throw new ErroCriacaoRegistro(msg);
		}
		
		return repository.save(setor);
	}

	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if(existe) {
			throw new RegraNegocioException("Nome já cadastrado!");
		}
	}
	@Override
	public Setor findById(Long id) {
		Optional<Setor> list = repository.findById(id);
		 Setor retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;
	}
	@Override
	public Funcionario recuperaResponsavel(Integer id) {
		return funcServ.findById(id);
	}
	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}
	@Override	
	@Transactional(readOnly = true)
	public List<Setor> buscaSetor(Setor filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}
	@Override
	public void deletaSetor(Setor registro) {
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
	public void desativaSetor(Setor registro) {
		registro.setAtivo(false);
		salvarSetor(registro);
	}
	@Override
	public void ativaSetor(Setor registro) {
		registro.setAtivo(true);
		salvarSetor(registro);
	}
	
}
