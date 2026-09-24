package hiragi.innovatracker.repository;

import hiragi.innovatracker.model.TdDepartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TdDepartamentoRepository extends JpaRepository<TdDepartamento, Long> {


    Optional<TdDepartamento> findBySglDepartamento(String sglCategoria);

    List<TdDepartamento> findBySglDepartamentoContainingOrNmeDepartamento(String sql, String nme);

    boolean existsBySglDepartamento(String sglCategoria);


    // Busca exata pelo nome
    Optional<TdDepartamento> findByNmeDepartamento(String nmeCategoria);


    boolean existsByNmeDepartamento(String nmeCategoria);


    // Busca parcial pelo nome (LIKE %valor%), ordenada
    List<TdDepartamento> findByNmeDepartamentoContainingIgnoreCaseOrderByNmeDepartamento(String nmeCategoria);


}
