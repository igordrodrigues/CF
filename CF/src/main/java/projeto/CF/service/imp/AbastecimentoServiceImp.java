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
import projeto.CF.model.entity.Abastecimento;
import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.AbastecimentoRepository;
import projeto.CF.model.repository.FuncionarioRepository;
import projeto.CF.service.AbastecimentoService;
import projeto.CF.service.CombustivelService;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.LocalService;
import projeto.CF.service.UsuarioService;

@Service
public class AbastecimentoServiceImp implements AbastecimentoService{

	
	@Autowired
	private AbastecimentoRepository repository;
	
	@Autowired
	private FuncionarioService funcServ;
	@Autowired
	private LocalService locServ;
	@Autowired
	private CombustivelService combServ;
	@Autowired
	private UsuarioService userServ;
	
	public AbastecimentoServiceImp(AbastecimentoRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Abastecimento salvarAbastecimento(Abastecimento abastecimento) {
		//define erros de captação de dados da aplicação
		if(abastecimento.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(abastecimento.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(abastecimento.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Combustivel = false, erro_Data = false, erro_Km = false, 
				erro_Litros = false, erro_Local = false, erro_Motorista = false, 
				erro_Valor = false;
		//verifica se os campos estão preenchidos
		if(abastecimento.getDataAbastecimento() == null) {erro_Data = true;}
		//verifica se o combustivel está vazio
		if(abastecimento.getCombustivel() == null) {erro_Combustivel = true;}
		//verifica se o KmRegistrado  está vazio
		if(abastecimento.getKmRegistrado() == null) {erro_Km = true;}
		//verifica se a litros está vazio
		if(abastecimento.getLitros() == null) {erro_Litros = true;}
		//verifica se a local está vazio
		if(abastecimento.getLocal() == null) {erro_Local = true;}
		//verifica se a motorista está vazio
		if(abastecimento.getMotorista() == null) {erro_Motorista = true;}
		//verifica se a valor está vazio
		if(abastecimento.getValor() == null) {erro_Valor = true;}

		if (erro_Combustivel || erro_Data || erro_Km || erro_Litros  || 
			erro_Local || erro_Motorista || erro_Valor ) {
			String msg = "";
			if(erro_Combustivel) {msg.concat("Tipo do combustivel obrigratorio!\n");}
			if(erro_Data) {msg.concat("Data de abastecimento obrigratorio!\n");}
			if(erro_Km) {msg.concat("km Registrado obrigratorio!\n");}
			if(erro_Litros) {msg.concat("litros obrigratorio!\n");}
			if(erro_Local) {msg.concat("local obrigratorio!\n");}
			if(erro_Motorista) {msg.concat("motorista obrigratorio!\n");}
			if(erro_Valor) {msg.concat("valor obrigratorio!\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
		
	}
	

		return repository.save(abastecimento);
	}

	@Override
	public void validarId(Long id) {
		Optional<Abastecimento> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Abastecimento findById(Long id) {
		Optional<Abastecimento> list = repository.findById(id);
		Abastecimento retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;

	}

	@Override
	public Funcionario recuperaFuncionario(Integer id) {
		return funcServ.findById(id);

	}

	@Override
	public Local recuperaLocal(Long id) {
		return locServ.findById(id);
	}

	@Override
	public Combustivel recuperaCombustivel(Long id) {
		return combServ.findById(id);
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<Abastecimento> buscaAbastecimento(Abastecimento filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaAbastecimento(Abastecimento registro) {
		
		if(!(repository.existsById(registro.getId()))) {
			throw new ErroIdentificacaoRegistro("registro não encontrado!");
		}
		repository.delete(registro);
	}

	@Override
	public void desativaAbastecimento(Abastecimento registro) {
		registro.setAtivo(false);
		salvarAbastecimento(registro);
	}

	@Override
	public void ativaAbastecimento(Abastecimento registro) {
		registro.setAtivo(true);
		salvarAbastecimento(registro);
		
	}
	

}
