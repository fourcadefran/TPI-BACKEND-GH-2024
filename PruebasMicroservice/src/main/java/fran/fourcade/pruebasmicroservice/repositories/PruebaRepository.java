package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PruebaRepository extends CrudRepository<Prueba, Long> {
    boolean existsByVehiculoAndFechaHoraInicio(Vehiculo vehiculo, String fechaHoraInicio);
    boolean existsByVehiculoAndFechaHoraFinIsNull(Vehiculo vehiculo);
    Iterable<Prueba> findAllByFechaHoraFinIsNull();
    List<Prueba> findPruebaByVehiculoId(Long id);
    Iterable<Prueba> findByExcedeLimitesTrue();
    Iterable<Prueba> findByIdEmpleadoAndExcedeLimitesTrue(int idEmpleado);
    Iterable<Prueba> findByVehiculoId(Long vehiculoId);
}
