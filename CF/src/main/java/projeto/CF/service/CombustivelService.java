package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Usuario;

public interface CombustivelService {

	Combustivel salvarCombustivel(Combustivel combustivel);
	Combustivel findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Combustivel> buscaCombustivel(Combustivel filtro);
	void deletaCombustivel(Combustivel registro);
	void desativaCombustivel(Combustivel registro);
	void ativaCombustivel(Combustivel registro);
}
