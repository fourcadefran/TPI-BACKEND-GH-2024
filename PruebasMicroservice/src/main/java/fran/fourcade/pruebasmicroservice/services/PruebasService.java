package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.dtos.PruebaDTO;
import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.InteresadoRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
    public Prueba convertirDtoAEntidad(PruebaDTO pruebaDTO) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(pruebaDTO.getVehiculo().getId());

        Interesado interesado = new Interesado();
        interesado.setId(pruebaDTO.getInteresado().getId());
        interesado.setTipoDocumento(pruebaDTO.getInteresado().getTipoDocumento());
        interesado.setDocumento(pruebaDTO.getInteresado().getDocumento());

        Prueba prueba = new Prueba();
        prueba.setVehiculo(vehiculo);
        prueba.setInteresado(interesado);
        prueba.setIdEmpleado(pruebaDTO.getIdEmpleado());
        prueba.setFechaHoraInicio(pruebaDTO.getFechaHoraInicio());
        prueba.setFechaHoraFin(pruebaDTO.getFechaHoraFin());
        prueba.setComentarios(pruebaDTO.getComentarios());

        return prueba;
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

    public Prueba create(PruebaDTO request) {
        Prueba prueba = convertirDtoAEntidad(request);
        Interesado interesado = interesadoRepository.findById(prueba.getInteresado().getId())
                .orElseThrow(() -> new ServiceException("No se encontró el interesado"));

        if (interesado.getRestringido()) {
            throw new ServiceException("El interesado está restringido y no puede realizar pruebas de vehículos.");
        }

        String licenciaFechaVencimiento = interesado.getFechaVencimientoLicencia();

        LocalDateTime fechaVencimientoDateTime = LocalDateTime.parse(licenciaFechaVencimiento);

        LocalDate fechaVencimiento = fechaVencimientoDateTime.toLocalDate();

        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new ServiceException("La licencia del interesado está vencida.");
        }

        Vehiculo vehiculo = vehiculoRepository.findById(prueba.getVehiculo().getId())
                .orElseThrow(() -> new ServiceException("No se encontró el vehículo"));

        //boolean isVehicleInUse = repository.existsByVehiculoAndFechaHoraInicio(vehiculo, "2025-11-22T09:00:00");
        boolean isVehicleInUse = repository.existsByVehiculoAndFechaHoraFinIsNull(vehiculo);
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
