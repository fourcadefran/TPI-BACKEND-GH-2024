package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Notificacion;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.repositories.NotificacionRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;
    private final PruebaRepository pruebaRepository;

    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository, PruebaRepository pruebaRepository) {
        this.notificacionRepository = notificacionRepository;
        this.pruebaRepository = pruebaRepository;
    }

    public Notificacion create(Notificacion notificacion) { return notificacionRepository.save(notificacion);}
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }

    public String registrarNotificacion(Prueba prueba, String mensaje) {
        Notificacion notificacion = new Notificacion(prueba, mensaje);
        notificacionRepository.save(notificacion);
        return "Notificación enviada";
    }
}
