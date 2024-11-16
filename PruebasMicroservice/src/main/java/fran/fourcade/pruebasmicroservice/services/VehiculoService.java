package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Modelo;
import fran.fourcade.pruebasmicroservice.models.Posicion;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.ModeloRepository;
import fran.fourcade.pruebasmicroservice.repositories.PosicionRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final PruebaRepository pruebaRepository;
    private final ModeloRepository modeloRepository;
    private final PosicionRepository posicionRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, PruebaRepository pruebaRepository, ModeloRepository modeloRepository, PosicionRepository posicionRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.pruebaRepository = pruebaRepository;
        this.modeloRepository = modeloRepository;
        this.posicionRepository = posicionRepository;
    }

    public Iterable<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }
    public Vehiculo getById(Long id) throws ServiceExceptionPrueba {
        return vehiculoRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("El vehiculo no existe"));
    }
    public Vehiculo create(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }
    public void delete(Long id) {
        vehiculoRepository.deleteById(id);
    }
    public Vehiculo update(Long id, Vehiculo vehiculoDetails) throws ServiceExceptionPrueba {
        Prueba prueba = pruebaRepository.findById(vehiculoDetails.getPrueba().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("La prueba no existe"));

        Modelo modelo = modeloRepository.findById(vehiculoDetails.getModelo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No existe el modelo."));
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("El vehiculo no existe"));
        vehiculo.setPrueba(prueba);
        vehiculo.setModelo(modelo);
        vehiculo.setPatente(vehiculoDetails.getPatente());
        return vehiculoRepository.save(vehiculo);

    }

    public Vehiculo posicionVehiculo(Long id, Posicion posicionDetails) throws ServiceExceptionPrueba {
        Posicion posicion = posicionRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontró la posicion"));

        Vehiculo vehiculo = vehiculoRepository.findById(posicion.getVehiculo().getId()).orElseThrow(() -> new ServiceExceptionPrueba("No se encontró el vehiculo"));
        boolean isInPrueba = vehiculoRepository.findVehiculoByPruebaExists();
        if (isInPrueba) {
            Prueba prueba = pruebaRepository.findPruebaByVehiculoId(vehiculo.getId());

            /*
            double radioPermitido = prueba.getRadioPermitido(); // En kilómetros
            Posicion centroPrueba = prueba.getCentro();

            // Calcular la distancia entre la posición actual y el centro de la prueba
            double distancia = calcularDistancia(centroPrueba, posicionDetails);

            if (distancia > radioPermitido) {
                // Almacenar una notificación sobre la violación del radio permitido
                registrarNotification(vehiculo, "El vehículo ha excedido el radio permitido.");
            }

            // Verificar si ingresó a una zona peligrosa
            if (esZonaPeligrosa(posicionDetails)) {
                registrarNotification(vehiculo, "El vehículo ha ingresado a una zona peligrosa.");
            }
            */
            //todo: si excedió los limites
            prueba.marcarExcesoDeLimites();
            pruebaRepository.save(prueba);
        }

        return vehiculo;
    }
}
