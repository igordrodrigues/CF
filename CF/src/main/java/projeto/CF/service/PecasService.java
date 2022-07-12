package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Pecas;
import projeto.CF.model.entity.Usuario;

public interface PecasService {

	Pecas salvarPecas(Pecas pecas);
	Pecas findById(Long id);
	void validarId(Long id);
	Usuario recuperaUsuario(Integer id);
	List<Pecas> buscaPecas(Pecas filtro);
	void deletaPecas(Pecas registro);
	void desativaPecas(Pecas registro);
	void ativaPecas(Pecas registro);
}
