package projeto.CF.model.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import projeto.CF.model.entity.Abastecimento;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Usuario;



public interface AbastecimentoRepository extends JpaRepository <Abastecimento, Long>{
	
	Optional<Abastecimento> findById(Long id);
	Optional<Abastecimento> findByDataAbastecimento(Date dataAbastecimento);
	Optional<Abastecimento> findByLocal(Long local);
	Optional<Abastecimento> findByMotorista(Funcionario motorista);
	Optional<Abastecimento> findByUsuario (Usuario user);
	boolean existsById(Long id);
	
}