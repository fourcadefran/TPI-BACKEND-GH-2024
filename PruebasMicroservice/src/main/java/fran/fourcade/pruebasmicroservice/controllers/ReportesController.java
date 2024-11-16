package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.services.PruebasService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    private final PruebasService pruebasService;

    @Autowired
    public ReportesController(PruebasService pruebasService) {
        this.pruebasService = pruebasService;
    }

    //1 - F - 1
    @GetMapping("/incidentes")
    public ResponseEntity<Iterable<Prueba>> getAllPruebasIncidentes() {
        try {
            return ResponseEntity.ok(pruebasService.getAllPruebasIncidentes());
        } catch (ServiceExceptionPrueba e ) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    //1 - F - 2
    @GetMapping("/incidentes/{idEmpleado}")
    public ResponseEntity<Iterable<Prueba>> getAllPruebasIncidentesByEmpleado(@PathVariable Long idEmpleado) {
        try {
            return ResponseEntity.ok(pruebasService.getAllPruebasIncidentesByEmpleado(idEmpleado));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    //1 - F - 4
    @GetMapping("/pruebas/{idVehiculo}")
    public ResponseEntity<Iterable<Prueba>> getAllPruebasByVehiculo(@PathVariable Long idVehiculo) {
        try {
            return ResponseEntity.ok(pruebasService.getAllPruebasByVehiculo(idVehiculo));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
