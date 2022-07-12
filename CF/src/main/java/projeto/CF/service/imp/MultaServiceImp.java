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
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Multa;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.model.repository.MultaRepository;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.MultaService;
import projeto.CF.service.UsuarioService;
import projeto.CF.service.VeiculoService;

@Service
public class MultaServiceImp implements MultaService {

	@Autowired
	private MultaRepository repository;
	@Autowired
	private VeiculoService veiculoServ;
	@Autowired
	private FuncionarioService funcServ;
	@Autowired
	private UsuarioService userServ;
	public MultaServiceImp (MultaRepository repository) {
		super();
		this.repository = repository;		
	}
	
	@Override
	@Transactional
	public Multa salvarMulta(Multa multa) {
		//define erros de captação de dados da aplicação
		if(multa.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(multa.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(multa.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		
		boolean erro_DataCadastro = false, erro_DataMulta = false, 
				erro_Local = false, erro_Motivo = false, erro_Motorista = false, 
				erro_Placa = false, erro_Valor = false;
		if(multa.getDataCadastro() == null) {erro_DataCadastro = true;}
		if(multa.getDataMulta() == null) {erro_DataMulta  = true;}
		if(multa.getLocal() == null) {erro_Local = true;}
		if(multa.getMotivo() == null || multa.getMotivo().isEmpty()) {erro_Motivo = true;}
		if(multa.getMotorista() == null) {erro_Motorista = true;}
		if(multa.getPlaca() == null) {erro_Placa  = true;}
		if(multa.getValor() == null) {erro_Valor = true;}
		
		if( erro_DataCadastro || erro_DataMulta ||erro_Local || 
			erro_Motivo || erro_Motorista || erro_Placa || erro_Valor ) {
			
			String msg = "";
			if(erro_DataCadastro) {msg.concat("Erro captação da Data Cadastro!\n");}
			if(erro_DataMulta) {msg.concat("Data Multa obrigratorio!\n");}
			if(erro_Local) {msg.concat("Local obrigratorio!\n");}
			if(erro_Motivo) {msg.concat("Motivo obrigratorio!\n");}
			if(erro_Motorista) {msg.concat("Motorista obrigratorio!\n");}
			if(erro_Placa) {msg.concat("Placa obrigratorio!\n");}
			if(erro_Valor ) {msg.concat("Valor obrigratorio!\n");}
			throw new ErroCriacaoRegistro(msg);
		}
		
		return repository.save(multa);
	}

	@Override
	public void validarId(Long id) {
		Optional<Multa> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Multa findById(Long id) {
		Optional<Multa> list = repository.findById(id);
		Multa retorno;
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
	public Funcionario recuperaFuncionario(Integer id) {
		return funcServ.findById(id);
	}

	@Override
	public Veiculo recuperaVeiculo(String id) {
		return veiculoServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<Multa> buscaMulta(Multa filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaMulta(Multa registro) {
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
	public void desativaMulta(Multa registro) {
		registro.setAtivo(false);
		salvarMulta(registro);
	}

	@Override
	public void ativaMulta(Multa registro) {
		registro.setAtivo(true);
		salvarMulta(registro);
	}

}
