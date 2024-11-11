package fran.fourcade.pruebasmicroservice.controllers;

import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.models.Modelo;
import fran.fourcade.pruebasmicroservice.services.PruebasService;
import fran.fourcade.pruebasmicroservice.services.VehiculoService;
import fran.fourcade.pruebasmicroservice.services.ModeloService;
import fran.fourcade.pruebasmicroservice.services.ServiceExceptionPrueba;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final ModeloService modeloService;
    private final PruebasService pruebasService;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService, ModeloService modeloService, PruebasService pruebasService) {
        this.vehiculoService = vehiculoService;
        this.modeloService = modeloService;
        this.pruebasService = pruebasService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Vehiculo>> getAllVehiculos() {
        return ResponseEntity.ok(vehiculoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Long id) {
        try {
            Vehiculo vehiculo = vehiculoService.getById(id);
            return ResponseEntity.ok(vehiculo);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            // Verifica si el modelo existe antes de crear el vehículo
            Modelo modelo = modeloService.getById(vehiculo.getModelo().getId());
            vehiculo.setModelo(modelo);
            return ResponseEntity.ok(vehiculoService.create(vehiculo));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.badRequest()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoDetails) {
        try {
            Vehiculo vehiculo = vehiculoService.getById(id);
            vehiculo.setPatente(vehiculoDetails.getPatente());

            // Verifica si el modelo existe antes de asignarlo al vehículo
            Modelo modelo = modeloService.getById(vehiculoDetails.getModelo().getId());
            vehiculo.setModelo(modelo);
            Prueba prueba = pruebasService.getById(vehiculoDetails.getPrueba().getId());
            vehiculo.setPrueba(prueba);

            return ResponseEntity.ok(vehiculoService.update(id, vehiculo));
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vehiculo> deleteVehiculo(@PathVariable Long id) {
        try {
            Vehiculo vehiculo = vehiculoService.getById(id);
            vehiculoService.delete(id);
            return ResponseEntity.ok(vehiculo);
        } catch (ServiceExceptionPrueba e) {
            return ResponseEntity.notFound()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }
}
