package projeto.CF.service;



import java.util.List;

import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Usuario;

public interface ModeloService {

	Modelo salvarModelo(Modelo modelo);
	Modelo findById(Long id);
	Marca recuperaMarca(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Modelo> buscaModelo(Modelo filtro);
	void deletaModelo(Modelo modelo);
	void desativaModelo(Modelo registro);
	void ativaModelo(Modelo registro);
}
