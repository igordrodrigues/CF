package projeto.CF.model.repository;


import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.OrdemServico;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;


public interface OrdemServicoRepository extends JpaRepository <OrdemServico, Long>{
	
	Optional<OrdemServico> findById(Long id);
	Optional<OrdemServico> findByDataFinal(Date dataFinal);
	Optional<OrdemServico> findByDataInicio(Date dataInicio);
	Optional<OrdemServico> findByFuncionario(Funcionario funcionario);
	Optional<OrdemServico> findByServico(TipoServico servico);
	Optional<OrdemServico> findByUsuario(Usuario usuario);
	Optional<OrdemServico> findByVeiculo(Veiculo veiculo);
	boolean existsById(Long id);
	boolean existsByServico(TipoServico servico);
	boolean existsByUsuario(Usuario usuario);
	boolean existsByVeiculo(Veiculo veiculo);
	
}
