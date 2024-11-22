package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Posicion;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import org.springframework.data.repository.CrudRepository;

public interface PosicionRepository extends CrudRepository<Posicion, Long> {
    Posicion findByVehiculo(Vehiculo vehiculo);
}
