package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.Usuario;

public interface SetorService {
	
	Setor salvarSetor(Setor setor);
	Setor findById(Long id);
	Funcionario recuperaResponsavel(Integer id);
	Usuario recuperaUsuario(Integer id);
	void validarNome(String nome);
	List<Setor> buscaSetor(Setor filtro);
	void deletaSetor(Setor registro);
	void desativaSetor(Setor registro);
	void ativaSetor(Setor registro);
}
