package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.InteresadoRepository;
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
    private final PruebaRepository pruebaRepository;

    @Autowired
    public PruebasService(PruebaRepository repository, InteresadoRepository interesadoRepository, VehiculoRepository vehiculoRepository , PruebaRepository pruebaRepository) {
        this.repository = repository;
        this.interesadoRepository = interesadoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.pruebaRepository = pruebaRepository;
    }

    public Iterable<Prueba> getAll() {
        return repository.findAll();
    }

    public Iterable<Prueba> getAllNow() {
        return repository.findAllByFechaHoraFinIsNull();
    }

    public Prueba getById(Long id) throws ServiceExceptionPrueba {
        return repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontró el id de la Prueba"));

    }

    public Prueba create(Prueba prueba) {
        Interesado interesado = interesadoRepository.findById(prueba.getInteresado().getId())
                .orElseThrow(() -> new ServiceException("No se encontró el interesado"));

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
        Prueba prueba = repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontró la Prueba"));

        prueba.setComentarios(pruebaDetails.getComentarios());
        prueba.setFechaHoraFin(pruebaDetails.getFechaHoraFin());
        prueba.setFechaHoraInicio(pruebaDetails.getFechaHoraInicio());
        prueba.setIdEmpleado(pruebaDetails.getIdEmpleado());

        Interesado interesado = interesadoRepository.findById(pruebaDetails.getInteresado().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontró el interesado"));
        prueba.setInteresado(interesado);

        Vehiculo vehiculo = vehiculoRepository.findById(pruebaDetails.getVehiculo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontró el vehiculo"));
        prueba.setVehiculo(vehiculo);

        return repository.save(prueba);
    }

    public Prueba finalizar(Long id, String comentarios) throws ServiceExceptionPrueba {
        Prueba prueba = repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontró la Prueba"));
        prueba.finalizar(comentarios);
        return repository.save(prueba);
    }


    public Iterable<Prueba> getAllPruebasIncidentes() throws ServiceExceptionPrueba {
        return pruebaRepository.findByExcedeLimitesTrue();
    }

    public Iterable<Prueba> getAllPruebasIncidentesByEmpleado(Long id) throws ServiceExceptionPrueba {
        return pruebaRepository.findByIdEmpleadoAndExcedeLimitesTrue(id.intValue());
    }

    public Iterable<Prueba>  getAllPruebasByVehiculo(Long idVehiculo) throws ServiceExceptionPrueba {
        if (idVehiculo == null || idVehiculo <= 0) {
            throw new ServiceExceptionPrueba("El ID del vehículo no es válido");
        }

        Iterable<Prueba> pruebas = pruebaRepository.findByVehiculoId(idVehiculo);
        if (!pruebas.iterator().hasNext()) {
            throw new ServiceExceptionPrueba("No se encontraron pruebas para el vehículo con ID: " + idVehiculo);
        }

        return pruebas;
    }

}
