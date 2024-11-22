package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
    Agencia findFirstBy();
}
