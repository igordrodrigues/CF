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
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.TipoEquipamentoRepository;
import projeto.CF.service.TipoEquipamentoService;
import projeto.CF.service.UsuarioService;

@Service
public class TipoEquipamentoServiceImp implements TipoEquipamentoService{

	@Autowired
	private TipoEquipamentoRepository repository;
	@Autowired
	private UsuarioService userServ;

	public TipoEquipamentoServiceImp(TipoEquipamentoRepository repository) {
		super();
		this.repository = repository;
		
	}
	
	@Override
	@Transactional
	public TipoEquipamento salvarTipoEquipamento(TipoEquipamento tipoEquipamento) {
		validarNome(tipoEquipamento.getNome());
		//define erros de captação de dados da aplicação
		if(tipoEquipamento.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(tipoEquipamento.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(tipoEquipamento.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}

		boolean erro_Nome = false, erro_Descricao = false;
		
		if(tipoEquipamento.getNome().isEmpty() || tipoEquipamento.getNome() == null) {erro_Nome = true;}
		if(tipoEquipamento.getDescricao().isEmpty() || tipoEquipamento.getDescricao() == null) {erro_Descricao = true;}
		
		if(erro_Descricao || erro_Nome) {
			String msg = "";
			if(erro_Descricao) {msg.concat("Descricao obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
			throw new ErroCriacaoRegistroFuncionario(msg);
			
		}

		return repository.save(tipoEquipamento);	
	}

	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if(existe) {
			throw new RegraNegocioException("Nome já cadastrado!");
		} 
	}

	@Override
	public TipoEquipamento findById(Long id) {
		Optional<TipoEquipamento> list = repository.findById(id);
		 TipoEquipamento retorno;
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
	public List<TipoEquipamento> buscaTipoEquipamento(TipoEquipamento filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaTipoEquipamento(TipoEquipamento registro) {
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
	public void desativaTipoEquipamento(TipoEquipamento registro) {
		registro.setAtivo(false);
		salvarTipoEquipamento(registro);
	}

	@Override
	public void ativaTipoEquipamento(TipoEquipamento registro) {
		registro.setAtivo(true);
		salvarTipoEquipamento(registro);
	}
	
}
