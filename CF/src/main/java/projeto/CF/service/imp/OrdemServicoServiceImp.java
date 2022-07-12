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
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.OrdemServico;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.model.repository.OrdemServicoRepository;
import projeto.CF.service.FuncionarioService;
import projeto.CF.service.ManutencaoService;
import projeto.CF.service.OrdemServicoService;
import projeto.CF.service.TipoServicoService;
import projeto.CF.service.UsuarioService;
import projeto.CF.service.VeiculoService;

@Service
public class OrdemServicoServiceImp implements OrdemServicoService{

	@Autowired
	private OrdemServicoRepository repository;
	@Autowired
	private FuncionarioService funcServ;
	@Autowired
	private ManutencaoService manuServ;
	@Autowired
	private TipoServicoService tsServ;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private VeiculoService veiculoServ;
	
	
	public OrdemServicoServiceImp (OrdemServicoRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public OrdemServico salvarOrdemServico(OrdemServico ordemServico) {
		//define erros de captação de dados da aplicação
		if(ordemServico.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(ordemServico.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(ordemServico.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_DataInicio = false, erro_DataFinal = false, erro_HoraInicio = false, 
				erro_HoraFim = false, erro_Funcionario = false, erro_Servico = false, 
				erro_TipoServico = false, erro_Veiculo = false;
		if(ordemServico.getDataInicio() == null) {erro_DataInicio  = true;}
		if(ordemServico.getDataFinal() == null) {erro_DataFinal = true;}
		if(ordemServico.getHora_inicio() == null) {erro_HoraInicio = true;}
		if(ordemServico.getHora_fim() == null) {erro_HoraFim = true;}
		if(ordemServico.getServico() == null) {erro_Servico = true;}
		if(ordemServico.getTipoServico() == null) {erro_TipoServico = true;}
		if(ordemServico.getVeiculo() == null) {erro_Veiculo = true;}
		if(ordemServico.getFuncionario() == null) {erro_Funcionario = true;}

	if(erro_DataInicio ||erro_DataFinal || erro_HoraInicio ||erro_HoraFim ||
		erro_Funcionario || erro_Servico ||erro_TipoServico ||  erro_Veiculo ) {
		String msg = "";
		if( erro_DataInicio) {msg.concat("Data de Inicio obrigratorio!\n");}
		if( erro_DataFinal) {msg.concat("Data de Final obrigratorio!\n");}
		if(erro_HoraInicio ) {msg.concat("horario de inicio obrigratorio!\n");}
		if( erro_HoraFim) {msg.concat("Horario final obrigratorio!\n");}
		if( erro_Funcionario) {msg.concat("Funcionario obrigratorio!\n");}
		if(erro_Servico ) {msg.concat("Servico obrigratorio!\n");}
		if(erro_TipoServico ) {msg.concat("Tipo de Servico obrigratorio!\n");}
		if(erro_Veiculo ) {msg.concat("Veiculo obrigratorio!\n");}
		throw new ErroCriacaoRegistro(msg);
		
		
		
		
	}
			
		return repository.save(ordemServico);
	}

	@Override
	public void validarId(Long id) {
		Optional<OrdemServico> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public OrdemServico findById(Long id) {
		Optional<OrdemServico> list = repository.findById(id);
		OrdemServico retorno;
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
	public Manutencao recuperaServico(Long id) {
		return manuServ.findById(id);
	}

	@Override
	public TipoServico recuperaTipoServico(Long id) {
		return tsServ.findById(id);
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override
	public Veiculo recuperaVeiculo(String id) {
		return veiculoServ.findById(id);
	}

	@Override	
	@Transactional(readOnly = true)
	public List<OrdemServico> buscaOrdemServico(OrdemServico filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaOrdemServico(OrdemServico registro) {
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
	public void desativaOrdemServico(OrdemServico registro) {
		registro.setAtivo(false);
		salvarOrdemServico(registro);
	}

	@Override
	public void ativaOrdemServico(OrdemServico registro) {
		registro.setAtivo(true);
		salvarOrdemServico(registro);
	}

}
