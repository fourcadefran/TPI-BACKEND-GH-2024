package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.dtos.NotificacionDTO;
import fran.fourcade.pruebasmicroservice.models.Notificacion;
import fran.fourcade.pruebasmicroservice.services.NotificacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {
    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping("/promocion")
    public ResponseEntity<Notificacion> promocionNotificacion(@RequestBody NotificacionDTO request) {
        return ResponseEntity.ok(notificacionService.promocionarNotificacion(request));
    }
}
