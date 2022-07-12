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
import projeto.CF.model.entity.Pneus;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.PneuRepository;
import projeto.CF.service.PneuService;
import projeto.CF.service.UsuarioService;

@Service
public class PneuServiceImp implements PneuService{

	@Autowired
	private PneuRepository repository;
	@Autowired
	private UsuarioService userServ;

	public PneuServiceImp (PneuRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Pneus salvarPneus(Pneus pneu) {
		validarModelo(pneu.getModelo());
		//define erros de captação de dados da aplicação
		if(pneu.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(pneu.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(pneu.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Marca = false, erro_Modelo = false;
		if(pneu.getModelo().isEmpty() || pneu.getModelo() == null) {erro_Modelo = true;}
		if(pneu.getMarca().isEmpty() || pneu.getMarca() == null) {erro_Marca = true;}
		
		if(erro_Modelo || erro_Marca) {
			String msg = "";
			if(erro_Modelo) {msg.concat("Modelo obrigratorio!\n");}
			if(erro_Marca) {msg.concat("Marca obrigratorio!\\n");}
			throw new ErroCriacaoRegistro(msg);
			
		}

		return repository.save(pneu);
	}

	@Override
	public void validarModelo(String modelo) {
		boolean existe = repository.existsByModelo(modelo);
		if(existe) {
			throw new RegraNegocioException("Moldeo Cadasrtado!");
			
		}
		
	}

	@Override
	public Pneus findById(Long id) {
		Optional<Pneus> list = repository.findById(id);
		 Pneus retorno;
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
	public List<Pneus> buscaPneus(Pneus filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaPneu(Pneus registro) {
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
	public void desativaPneus(Pneus registro) {
		registro.setAtivo(false);
		salvarPneus(registro);
	}

	@Override
	public void ativaPneus(Pneus registro) {
		registro.setAtivo(true);
		salvarPneus(registro);
	}
	

}
