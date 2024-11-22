package fran.fourcade.pruebasmicroservice.repositories;

import fran.fourcade.pruebasmicroservice.models.Notificacion;
import org.springframework.data.repository.CrudRepository;


public interface NotificacionRepository extends CrudRepository<Notificacion, Long> {
}
