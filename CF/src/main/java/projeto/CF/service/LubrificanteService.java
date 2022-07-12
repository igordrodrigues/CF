package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Lubrificante;
import projeto.CF.model.entity.Usuario;

public interface LubrificanteService {

	Lubrificante salvarLubrificante(Lubrificante lubrificante);
	Lubrificante findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Lubrificante> buscaLubrificante(Lubrificante filtro);
	void deletaLubrificante(Lubrificante registro);
	void desativaLubrificante(Lubrificante registro);
	void ativaLubrificante(Lubrificante registro);

}
