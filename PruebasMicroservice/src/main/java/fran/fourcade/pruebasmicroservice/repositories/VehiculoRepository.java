package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long> {
    Optional<Vehiculo> findVehiculoByPruebasIsNotNull(); // todo: chequear
}
