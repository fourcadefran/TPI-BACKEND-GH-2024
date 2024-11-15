package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import org.springframework.data.repository.CrudRepository;

public interface PruebaRepository extends CrudRepository<Prueba, Long> {
    boolean existsByVehiculoAndFechaHoraInicio(Vehiculo vehiculo, String fechaHoraInicio);
    Iterable<Prueba> findAllByFechaHoraFinEmpty();
}
