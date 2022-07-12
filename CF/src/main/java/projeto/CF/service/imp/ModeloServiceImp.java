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
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Multa;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.ModeloRepository;
import projeto.CF.service.MarcaService;
import projeto.CF.service.ModeloService;
import projeto.CF.service.UsuarioService;

@Service
public class ModeloServiceImp implements ModeloService {

	@Autowired
	private ModeloRepository repository;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private MarcaService marcServ;
	public ModeloServiceImp (ModeloRepository repository) {
		super();
		this.repository = repository;
	}
	@Override
	@Transactional
	public Modelo salvarModelo(Modelo modelo) {
		//define erros de captação de dados da aplicação
		if(modelo.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(modelo.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(modelo.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Abrv = false, erro_Marca = false;
		if(modelo.getNome().isEmpty() || modelo.getNome() == null) {erro_Nome = true;}
		if(modelo.getAbreviatura().isEmpty() ||  modelo.getAbreviatura()== null) {erro_Abrv = true;}
		if(modelo.getMarca() == null) {erro_Marca = true;}
		
		if(erro_Abrv || erro_Nome || erro_Marca) {
			String msg = "";
			if(erro_Abrv) {msg.concat("Abreviatura é obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			if(erro_Marca) {msg.concat("Marca obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
			
		}


		return repository.save(modelo);
	}

	@Override
	public void validarId(Long id) {
		Optional<Modelo> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}
	@Override
	public Modelo findById(Long id) {
		Optional<Modelo> list = repository.findById(id);
		Modelo retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;
	}
	@Override
	public Marca recuperaMarca(Long id) {
		return marcServ.findById(id);
	}
	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}
	@Override	
	@Transactional(readOnly = true)
	public List<Modelo> buscaModelo(Modelo filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}
	@Override
	public void deletaModelo(Modelo registro) {
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
	public void desativaModelo(Modelo registro) {
		registro.setAtivo(false);
		salvarModelo(registro);
	}
	@Override
	public void ativaModelo(Modelo registro) {
		registro.setAtivo(true);
		salvarModelo(registro);
	}
	
	
}
