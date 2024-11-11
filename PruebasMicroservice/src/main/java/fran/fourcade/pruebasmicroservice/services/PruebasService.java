package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.InteresadoRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PruebasService {
    private final PruebaRepository repository;
    private final InteresadoRepository interesadoRepository;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public PruebasService(PruebaRepository repository, InteresadoRepository interesadoRepository, VehiculoRepository vehiculoRepository) {
        this.repository = repository;
        this.interesadoRepository = interesadoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public Iterable<Prueba> getAll() {
        return repository.findAll();
    }

    public Prueba getById(Long id) throws ServiceExceptionPrueba {
        return repository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el id de la Prueba"));

    }

    public Prueba create(Prueba prueba) {
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

}
