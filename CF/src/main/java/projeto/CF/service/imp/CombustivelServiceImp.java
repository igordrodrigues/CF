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
import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.CombustivelRepository;
import projeto.CF.service.CombustivelService;
import projeto.CF.service.UsuarioService;
@Service
public class CombustivelServiceImp implements CombustivelService{
	
	@Autowired
	private CombustivelRepository repository;
	@Autowired
	private UsuarioService userServ;

	public CombustivelServiceImp(CombustivelRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Combustivel salvarCombustivel(Combustivel combustivel) {
		//define erros de captação de dados da aplicação
		if(combustivel.getUsuario() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario!");}
		if(combustivel.getUsuarioAlteracao() == null) {throw new ErroCriacaoRegistro("Erro na captação de Usuario de alteração!");}
		if(combustivel.getDataCadastro() == null) {throw new ErroCriacaoRegistro("Erro na captação da data dos sistema!");}
		boolean erro_Nome = false, erro_Descricao = false;
		if(combustivel.getNome().isEmpty() || combustivel.getNome() == null) {erro_Nome = true;}
		if(combustivel.getDescricao().isEmpty() || combustivel.getDescricao() == null) {erro_Descricao = true;}
		
		if(erro_Nome || erro_Descricao ) {
			String msg = "";
			if(erro_Nome) {msg.concat("Nome do combustivel obrigratorio!\n");}
			if(erro_Descricao) {msg.concat("Descrição do combustivel obrigratorio!\n");}
			throw new ErroCriacaoRegistro(msg);
		}
		
		return repository.save(combustivel);
	}

	@Override
	public void validarId(Long id) {
		Optional<Combustivel> existe = repository.findById(id);
		if(existe.isEmpty()) {
			throw new ErroIdentificacaoRegistro("Identificação de Registro invalida!");
		}
	}

	@Override
	public Combustivel findById(Long id) {
		Optional<Combustivel> list = repository.findById(id);
		Combustivel retorno;
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
	public List<Combustivel> buscaCombustivel(Combustivel filtro) {
		Example example= Example.of(filtro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING)
				);
		return  repository.findAll(example);
	}

	@Override
	public void deletaCombustivel(Combustivel registro) {
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
	public void desativaCombustivel(Combustivel registro) {
		registro.setAtivo(false);
		salvarCombustivel(registro);
		
	}

	@Override
	public void ativaCombustivel(Combustivel registro) {
		registro.setAtivo(true);
		salvarCombustivel(registro);
	}
	

}
