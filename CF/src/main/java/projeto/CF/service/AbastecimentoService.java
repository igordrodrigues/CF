package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.Abastecimento;
import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Usuario;

public interface AbastecimentoService {

	Abastecimento salvarAbastecimento(Abastecimento abastecimento);
	Abastecimento findById(Long id);
	Funcionario recuperaFuncionario(Integer id);
	Local recuperaLocal(Long id);
	Combustivel recuperaCombustivel(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Abastecimento> buscaAbastecimento(Abastecimento filtro);
	void deletaAbastecimento(Abastecimento registro);
	void desativaAbastecimento(Abastecimento registro);
	void ativaAbastecimento(Abastecimento registro);


}
