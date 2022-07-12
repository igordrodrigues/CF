package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Usuario;

public interface FuncionarioService {

	Funcionario salvarFuncionario(Funcionario funcionario);
	Funcionario findById(Integer chapa);
	Usuario recuperaUsuario(Integer id);
	void validarChapa(Integer chapa);
	List<Funcionario> buscaFuncionario(Funcionario filtro);
	void deletaFuncionario(Funcionario registro);
	void desativaFuncionario(Funcionario registro);
	void ativaFuncionario(Funcionario registro);


}
