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
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.TipoServicoRepository;
import projeto.CF.service.TipoServicoService;
import projeto.CF.service.UsuarioService;

@Service
public class TipoServicoServiceImp implements TipoServicoService{

	@Autowired
	private TipoServicoRepository repository;
	@Autowired
	private UsuarioService userServ;

	public TipoServicoServiceImp (TipoServicoRepository repository) {
		super();
		this.repository = repository;
	}
	@Override
	@Transactional
	public TipoServico salvarTipoServico(TipoServico tipoServico) {
		validarNome(tipoServico.getNome());
		//define erros de captação de dados da aplicação
		if(tipoServico.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(tipoServico.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(tipoServico.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Descricao = false;

		if(tipoServico.getNome().isEmpty() || tipoServico.getNome() == null) {erro_Nome = true;}
		if(tipoServico.getDescricao().isEmpty() || tipoServico.getDescricao() == null) {erro_Descricao = true;}
		
		if(erro_Descricao || erro_Nome) {
			String msg = "";
			if(erro_Descricao) {msg.concat("Descricao obrigratorio!\n");}
			if(erro_Nome) {msg.concat("Nome obrigratorio!\\n");}
		throw new ErroCriacaoRegistroFuncionario(msg);
			
		}

		return repository.save(tipoServico);
	
	}

	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if(existe) {
			throw new RegraNegocioException("Chapa cadastrada!");
		} 
	}
	@Override
	public TipoServico findById(Long id) {
		Optional<TipoServico> list = repository.findById(id);
		TipoServico retorno;
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
	public List<TipoServico> buscaTipoServico(TipoServico filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);	}
	@Override
	public void deletaTipoSevico(TipoServico registro) {
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
	public void desativaTipoServico(TipoServico registro) {
		registro.setAtivo(false);
		salvarTipoServico(registro);
	}
	@Override
	public void ativaTipoServico(TipoServico registro) {
		registro.setAtivo(true);
		salvarTipoServico(registro);
	}
	

}
