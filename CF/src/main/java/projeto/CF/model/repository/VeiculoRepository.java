package projeto.CF.model.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository <Veiculo, String>{
	
	
	Optional<Veiculo> findByAno(Integer ano);
	Optional<Veiculo> findByMarca(Marca marca);
	Optional<Veiculo> findByModelo(Modelo modelo);
	Optional<Veiculo> findByNumero(Integer numero);
	Optional<Veiculo> findByPlaca(String placa);
	Optional<Veiculo> findBySetor(Setor setor);
	Optional<Veiculo> findByTipo(TipoEquipamento tipo);
	Optional<Veiculo> findByRenavan(Integer renavan);
	boolean existsBySetor(Setor setor);
	boolean existsByPlaca(String placa);
	boolean existsByModelo(Modelo modelo);
	boolean existsByNumero(Integer numero);
	boolean existsByChassi(String chassi);
	boolean existsByRenavan(Integer renavan);

}
