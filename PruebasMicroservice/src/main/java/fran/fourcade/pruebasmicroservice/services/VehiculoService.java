package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.*;
import fran.fourcade.pruebasmicroservice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final PruebaRepository pruebaRepository;
    private final ModeloRepository modeloRepository;
    private final PosicionRepository posicionRepository;
    private final AgenciaRepository agenciaRepository;
    private final NotificacionService notificacionService;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, PruebaRepository pruebaRepository, ModeloRepository modeloRepository, PosicionRepository posicionRepository, AgenciaRepository agenciaRepository, NotificacionService notificacionService) {
        this.vehiculoRepository = vehiculoRepository;
        this.pruebaRepository = pruebaRepository;
        this.modeloRepository = modeloRepository;
        this.posicionRepository = posicionRepository;
        this.agenciaRepository = agenciaRepository;
        this.notificacionService = notificacionService;
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

    public Vehiculo posicionVehiculo(Long id, Posicion posicionActual) throws ServiceExceptionPrueba {

        Posicion posicion = posicionRepository.findById(id)
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontró la posición"));


        Vehiculo vehiculo = vehiculoRepository.findById(posicion.getVehiculo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontró el vehículo"));


        Optional<Prueba> pruebaOpt = pruebaRepository.findPruebaByVehiculoId(vehiculo.getId());
        if (pruebaOpt.isEmpty()) {
            throw new ServiceExceptionPrueba("no se encontro una prueba para este vehiculo");
        }
        Prueba prueba = pruebaOpt.get();

        Agencia agencia = agenciaRepository.findFirstBy();
        double radioPermitido = agencia.getRadioAdmitidoKm();


        double latAgencia = agencia.getCoordenadasAgencia().getLat();
        double lonAgencia = agencia.getCoordenadasAgencia().getLon();

        double distancia = calcularDistanciaEuclidiana(latAgencia, lonAgencia, posicionActual);

        if (distancia > radioPermitido) {
            System.out.println("El vehículo ha excedido el radio permitido.");
            prueba.marcarExcesoDeLimites();
            pruebaRepository.save(prueba);
            notificacionService.registrarNotificacion(prueba, "El vehículo ha excedido el radio permitido.");
        }

        if (esZonaPeligrosa(agencia, posicionActual)) {
            System.out.println("El vehículo ha ingresado a una zona peligrosa.");
            notificacionService.registrarNotificacion(prueba, "El vehículo ha ingresado a una zona peligrosa.");
        }

        return vehiculo;
    }

    private boolean esZonaPeligrosa( Agencia agencia, Posicion posicion) {
        for (ZonaRestringida zona : agencia.getZonasRestringidas()) {
            if (posicion.getLatitud() >= zona.getNoroeste().getLat()
                    && posicion.getLatitud() <= zona.getSureste().getLat()
                    && posicion.getLongitud() >= zona.getNoroeste().getLon()
                    && posicion.getLongitud() <= zona.getSureste().getLon()) {
                return true;
            }
        }
        return false;
    }

    private double calcularDistanciaEuclidiana(double latAgencia, double lonAgencia, Posicion p2) {

        double diffLat = latAgencia - p2.getLatitud();
        double diffLon = lonAgencia - p2.getLongitud();

        return Math.sqrt(diffLat * diffLat + diffLon * diffLon);
    }
}
