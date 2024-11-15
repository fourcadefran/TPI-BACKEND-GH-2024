package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Posicion;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.InteresadoRepository;
import fran.fourcade.pruebasmicroservice.repositories.PosicionRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import java.time.LocalDate;

@Service
public class PruebasService {
    private final PruebaRepository repository;
    private final InteresadoRepository interesadoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PosicionRepository posicionRepository;

    @Autowired
    public PruebasService(PruebaRepository repository, InteresadoRepository interesadoRepository, VehiculoRepository vehiculoRepository, PosicionRepository posicionRepository) {
        this.repository = repository;
        this.interesadoRepository = interesadoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.posicionRepository = posicionRepository;
    }

    public Iterable<Prueba> getAll() {
        return repository.findAll();
    }

    public Iterable<Prueba> getAllNow() {
        return repository.findAllByFechaHoraFinEmpty();
    }

    public Prueba getById(Long id) throws ServiceExceptionPrueba {
        return repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el id de la Prueba"));

    }

    public Prueba create(Prueba prueba) {
        Interesado interesado = interesadoRepository.findById(prueba.getInteresado().getId())
                .orElseThrow(() -> new ServiceException("No se encontro el interesado"));

        if (interesado.getRestringido()) {
            throw new ServiceException("El interesado está restringido y no puede realizar pruebas de vehículos.");
        }

        String licenciaFechaVencimiento = interesado.getFechaVencimientoLicencia();
        LocalDate fechaExpiracion;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaExpiracion = LocalDate.parse(licenciaFechaVencimiento, formatter);
        } catch (DateTimeParseException e) {
            throw new ServiceException("El formato de la fecha de licencia es inválido.");
        }

        if (fechaExpiracion.isBefore(LocalDate.now())) {
            throw new ServiceException("La licencia del interesado está vencida.");
        }

        Vehiculo vehiculo = vehiculoRepository.findById(prueba.getVehiculo().getId())
                .orElseThrow(() -> new ServiceException("No se encontró el vehículo"));

        boolean isVehicleInUse = repository.existsByVehiculoAndFechaHoraInicio(vehiculo, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (isVehicleInUse) {
            throw new ServiceException("El vehículo ya está en prueba en este momento.");
        }
        return repository.save(prueba);

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Prueba update(Long id, Prueba pruebaDetails) throws ServiceExceptionPrueba {
        Prueba prueba = repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro la Prueba"));

        prueba.setComentarios(pruebaDetails.getComentarios());
        prueba.setFechaHoraFin(pruebaDetails.getFechaHoraFin());
        prueba.setFechaHoraInicio(pruebaDetails.getFechaHoraInicio());
        prueba.setIdEmpleado(pruebaDetails.getIdEmpleado());

        Interesado interesado = interesadoRepository.findById(pruebaDetails.getInteresado().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el interesado"));
        prueba.setInteresado(interesado);

        Vehiculo vehiculo = vehiculoRepository.findById(pruebaDetails.getVehiculo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el vehiculo"));
        prueba.setVehiculo(vehiculo);

        return repository.save(prueba);
    }

    public Prueba finalizar(Long id, String comentarios) throws ServiceExceptionPrueba {
        Prueba prueba = repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro la Prueba"));
        prueba.finalizar(comentarios);
        return repository.save(prueba);
    }
    public Vehiculo posicionVehiculo(Long id, Posicion posicionDetails) throws ServiceExceptionPrueba {
        Posicion posicion = posicionRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro la posicion"));

        Vehiculo vehiculo = vehiculoRepository.findById(posicion.getVehiculo().getId()).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el vehiculo"));
        boolean isInPrueba = vehiculoRepository.findVehiculoByPruebaExists();
        if (isInPrueba) {
            int lat = posicionDetails.getLatitud();
            int log = posicionDetails.getLongitud();
            // todo: if (lat < vehiculo.get)
        }

        return vehiculo;
    }

}
