package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import org.springframework.data.repository.CrudRepository;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {
    boolean findVehiculoByPruebaExists(); // todo: chequear
}
