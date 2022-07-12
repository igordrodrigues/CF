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
import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.MarcaRepository;
import projeto.CF.service.MarcaService;
import projeto.CF.service.UsuarioService;

@Service
public class MarcaServiceImp implements MarcaService{
	
	@Autowired
	private MarcaRepository repository;
	@Autowired
	private UsuarioService userServ;

	public MarcaServiceImp (MarcaRepository repository) {
		super();
		this.repository = repository;
	}
	

	@Override
	@Transactional
	public Marca salvarMarca(Marca marca) {
		//define erros de captação de dados da aplicação
		if(marca.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(marca.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(marca.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Abrv = false;
		if(marca.getNome().isEmpty() || marca.getNome() == null) {erro_Nome = true;}
		if(marca.getAbreviatura().isEmpty() ||  marca.getAbreviatura()== null) {erro_Abrv = true;}
		
		if(erro_Abrv || erro_Nome) {
			String msg = "";
			if(erro_Abrv) {msg.concat("Abreviatura é obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
			
		}

		return repository.save(marca);
	}

	@Override
	public void validarId(Long id) {
		Optional<Marca> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}


	@Override
	public Marca findById(Long id) {
		Optional<Marca> list = repository.findById(id);
		Marca retorno;
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
	public List<Marca> buscaMarca(Marca filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}


	@Override
	public void deletaMarca(Marca registro) {
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
	public void desativaMarca(Marca registro) {
		registro.setAtivo(false);
		salvarMarca(registro);
	}


	@Override
	public void ativaMarca(Marca registro) {
		registro.setAtivo(true);
		salvarMarca(registro);
	}
	
}
