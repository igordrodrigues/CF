package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Pneus;
import projeto.CF.model.entity.Usuario;

public interface PneuService {

	Pneus salvarPneus(Pneus pneus);
	Pneus findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarModelo(String modelo);
	List<Pneus> buscaPneus(Pneus filtro);
	void deletaPneu(Pneus registro);
	void desativaPneus(Pneus registro);
	void ativaPneus(Pneus registro);
}
