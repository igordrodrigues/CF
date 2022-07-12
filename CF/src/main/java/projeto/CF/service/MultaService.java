package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Multa;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;

public interface MultaService {

	Multa salvarMulta(Multa multa);
	Multa findById(Long id);
	void validarId(Long id);
	Usuario recuperaUsuario(Integer id);
	Funcionario recuperaFuncionario(Integer id);
	Veiculo recuperaVeiculo(String id);
	List<Multa> buscaMulta(Multa filtro);
	void deletaMulta(Multa registro);
	void desativaMulta(Multa registro);
	void ativaMulta(Multa registro);
}
