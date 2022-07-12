package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.Usuario;

public interface ManutencaoService {

	Manutencao salvarManutencao(Manutencao manutencao);
	Manutencao findById(Long id);
	Local recuperaLocal(Long id);
	Usuario recuperaUsuario(Integer id);
	Funcionario recuperaMotorista(Integer id);
	void validarId(Long id);
	List<Manutencao> buscaManutencao(Manutencao filtro);
	void deletaManutencao(Manutencao registro);
	void desativaManutencao(Manutencao registro);
	void ativaManutencao(Manutencao registro);
}
