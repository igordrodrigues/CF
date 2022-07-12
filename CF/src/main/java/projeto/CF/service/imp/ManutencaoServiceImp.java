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
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.ManutencaoRepository;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.LocalService;
import projeto.CF.service.ManutencaoService;
import projeto.CF.service.UsuarioService;

@Service
public class ManutencaoServiceImp implements ManutencaoService{
	
	@Autowired
	private ManutencaoRepository repository;	
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private LocalService locServ;
	@Autowired
	private FuncionarioService funcServ;
	public ManutencaoServiceImp (ManutencaoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Manutencao salvarManutencao(Manutencao manutencao) {
		//define erros de captação de dados da aplicação
		if(manutencao.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(manutencao.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(manutencao.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		
		boolean erro_Data = false, erro_Km = false, erro_Local = false, 
				erro_Motorista = false, erro_Valor = false;
		if(manutencao.getDataManutencao() == null) {erro_Data = true;}
		if(manutencao.getKmRegistrado() == null) {erro_Km = true;}
		if(manutencao.getLocal() == null) {erro_Local = true;}
		if(manutencao.getMotorista() == null) {erro_Motorista = true;}
		if(manutencao.getValor() == null) {erro_Valor = true;}
		
		if(erro_Data || erro_Km || erro_Local || erro_Motorista || erro_Valor ) {
			String msg = "";
			if(erro_Data) {msg.concat("Data obrigratorio!\n");}
			if(erro_Km) {msg.concat("Km obrigratorio!\n");}
			if(erro_Local) {msg.concat("Local obrigratorio!\n");}
			if(erro_Motorista) {msg.concat("Motorista obrigratorio!\n");}
			if(erro_Valor) {msg.concat("Valor obrigratorio!\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
		}
		
		return repository.save(manutencao);
	}

	@Override
	public void validarId(Long id) {
		Optional<Manutencao> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Manutencao findById(Long id) {
		Optional<Manutencao> list = repository.findById(id);
		Manutencao retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;
	}

	@Override
	public Local recuperaLocal(Long id) {
		return locServ.findById(id);
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override
	public Funcionario recuperaMotorista(Integer id) {
		return funcServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<Manutencao> buscaManutencao(Manutencao filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaManutencao(Manutencao registro) {
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
	public void desativaManutencao(Manutencao registro) {
		registro.setAtivo(false);
		salvarManutencao(registro);
	}

	@Override
	public void ativaManutencao(Manutencao registro) {
		registro.setAtivo(true);
		salvarManutencao(registro);
	}
	

	
}
