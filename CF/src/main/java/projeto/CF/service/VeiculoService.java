package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;

public interface VeiculoService {
	
	Veiculo salvarVeiculo(Veiculo veiculo);
	Veiculo findById(String placa);
	void validarPlaca(String placa);
	void validarChassi(String chassi);
	void validarRenavan(Integer renavan);
	void validarNumero(Integer numero);
	Cor recuperaCor(Long id);
	Marca recuperaMarca(Long id);
	Modelo recuperaModelo(Long id);
	Usuario recuperaUsuario(Integer id);
	Setor recuperaSetor(Long id);
	TipoEquipamento recuperaTipoEquipamento(Long id);
	List<Veiculo> buscaVeiculo(Veiculo filtro);
	void deletarVeiculo(Veiculo veiculo);
	void desativaVeiculo(Veiculo registro);
	void ativaVeiculo(Veiculo registro);
}
