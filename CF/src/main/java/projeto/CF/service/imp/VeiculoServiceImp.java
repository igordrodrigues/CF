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
import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.model.repository.VeiculoRepository;
import projeto.CF.service.CorService;
import projeto.CF.service.MarcaService;
import projeto.CF.service.ModeloService;
import projeto.CF.service.SetorService;
import projeto.CF.service.TipoEquipamentoService;
import projeto.CF.service.UsuarioService;
import projeto.CF.service.VeiculoService;

@Service
public class VeiculoServiceImp implements VeiculoService {
	
	@Autowired
	private VeiculoRepository repository;
	@Autowired
	private ModeloService modServ;
	@Autowired
	private MarcaService  marServ;
	@Autowired
	private CorService corServ;
	@Autowired
	private UsuarioService userServ;
	@Autowired
	private SetorService setorServ;
	@Autowired
	private TipoEquipamentoService teServ;
	public VeiculoServiceImp (VeiculoRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	@Transactional
	public Veiculo salvarVeiculo(Veiculo veiculo) {
		//define erros de captação de dados da aplicação
		if(veiculo.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(veiculo.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(veiculo.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}

		validarPlaca(veiculo.getPlaca());
		validarChassi(veiculo.getChassi());
		validarRenavan(veiculo.getRenavan());
		validarNumero(veiculo.getNumero());
		boolean erro_Ano = false, erro_Chassi = false,	erro_Cor = false, 
				erro_Marca = false, erro_Modelo = false, erro_Numero = false,
				erro_Placa = false, erro_Renavan = false, erro_Setor = false, 
				erro_Tipo = false;

		if(veiculo.getAno() == null) {erro_Ano = true;}
		if(veiculo.getChassi().isEmpty() || veiculo.getChassi() == null) {erro_Chassi = true;}
		if(veiculo.getCor() == null) {erro_Cor = true;}
		if(veiculo.getMarca() == null) {erro_Marca = true;}
		if(veiculo.getModelo() == null) {erro_Modelo = true;}
		if(veiculo.getNumero() == null) {erro_Numero = true;}
		if(veiculo.getPlaca().isEmpty() || veiculo.getPlaca() == null) {erro_Placa = true;}
		if(veiculo.getRenavan() == null) {erro_Renavan = true;}
		if(veiculo.getSetor() == null) {erro_Setor = true;}
		if(veiculo.getTipo() == null) {erro_Tipo = true;}

		if(erro_Ano ||erro_Chassi ||	erro_Cor ||
				erro_Marca ||erro_Modelo ||erro_Numero ||
				erro_Placa ||erro_Renavan ||erro_Setor ||
				erro_Tipo ) {
			String msg = "";
			
			if(erro_Ano) {msg.concat("Ano obrigratorio!\n");}
			if(erro_Chassi) {msg.concat("Chassi obrigratorio!\n");}
			if(erro_Cor) {msg.concat("Cor obrigratorio!\n");}
			if(erro_Marca) {msg.concat("Marca obrigratorio!\n");}
			if(erro_Modelo) {msg.concat("Modelo obrigratorio!\n");}
			if(erro_Numero) {msg.concat("Numero obrigratorio!\n");}
			if(erro_Placa) {msg.concat("Placa obrigratorio!\n");}
			if(erro_Renavan) {msg.concat("Renavan obrigratorio!\n");}
			if(erro_Setor) {msg.concat("Setor obrigratorio!\n");}
			if(erro_Tipo) {msg.concat("Tipo obrigratorio!\n");}
			throw new ErroCriacaoRegistro(msg);
		}

		return repository.save(veiculo);	}

	@Override
	public void validarPlaca(String placa) {
		boolean existe = repository.existsByPlaca(placa);
		if(existe) {
			throw new RegraNegocioException("Placa já cadastrada!");
		} 
	}
	
	@Override
	public void validarChassi(String chassi) {
		boolean existe = repository.existsByPlaca(chassi);
		if(existe) {
			throw new RegraNegocioException("Placa já cadastrada!");
		} 
	}

	@Override
	public void validarRenavan(Integer renavan) {
		boolean existe = repository.existsByRenavan(renavan);
		if(existe) {
			throw new RegraNegocioException("Placa já cadastrada!");
		} 
	}
	@Override
	public void validarNumero(Integer numero) {
		boolean existe = repository.existsByNumero(numero);
		if(existe) {
			throw new RegraNegocioException("Placa já cadastrada!");
		} 
	}

	@Override
	public Veiculo findById(String placa) {
		Optional<Veiculo> list = repository.findById(placa);
		 Veiculo retorno;
		if(list.isPresent()) {
			retorno = list.get();
		}else {
			throw new ErroIdentificacaoRegistro("Usuario não encontrado!");
		}
		return retorno;
	}

	@Override
	public Cor recuperaCor(Long id) {
		return corServ.findById(id);
	}

	@Override
	public Marca recuperaMarca(Long id) {
		return marServ.findById(id);
	}
	
	@Override
	public Modelo recuperaModelo(Long id) {
		return modServ.findById(id);
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		return userServ.findById(id);
	}

	@Override
	public Setor recuperaSetor(Long id) {
		return setorServ.findById(id);
	}

	@Override
	public TipoEquipamento recuperaTipoEquipamento(Long id) {
		return teServ.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Veiculo> buscaVeiculo(Veiculo filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletarVeiculo(Veiculo registro) {
		// TODO testar se existe vinculo do registro (ErroVinculoDetectado)
		//-------------------
		//-------------------
		//-------------------
		//-------------------
		if(!(repository.existsById(registro.getPlaca()))) {
			throw new ErroIdentificacaoRegistro("veiculo inexistente!");
		}
		repository.delete(registro);
	}

	@Override
	public void desativaVeiculo(Veiculo registro) {
		registro.setAtivo(false);
		salvarVeiculo(registro);
		}

	@Override
	public void ativaVeiculo(Veiculo registro) {
		registro.setAtivo(true);
		salvarVeiculo(registro);;
	}


}
