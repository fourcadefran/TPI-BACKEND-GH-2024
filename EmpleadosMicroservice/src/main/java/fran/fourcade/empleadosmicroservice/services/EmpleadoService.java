package fran.fourcade.empleadosmicroservice.services;

import fran.fourcade.empleadosmicroservice.models.Empleado;
import fran.fourcade.empleadosmicroservice.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository repository;

    @Autowired
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public Iterable<Empleado> getAll() {
        return repository.findAll();
    }

    public Empleado getById(Long id) throws ServiceException {
        return repository.findById(id).orElseThrow(() -> new ServiceException("No se encontro el id de la empleado"));
    }

    public List<Empleado> getByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
    public List<Empleado> getByApellido(String apellido) {
        return repository.findByApellido(apellido);
    }
    public Empleado create(Empleado empleado) {
        return repository.save(empleado);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
    public Empleado update(Long id, Empleado empleadoDetails) throws ServiceException {
        Empleado empleado = repository.findById(id).orElseThrow(() -> new ServiceException("No se encontro el id de la empleado"));

        empleado.setLegajo(empleadoDetails.getLegajo()); // todo: chequear
        empleado.setNombre(empleadoDetails.getNombre());
        empleado.setApellido(empleadoDetails.getApellido());
        empleado.setTelefonoContacto(empleadoDetails.getTelefonoContacto());
        return repository.save(empleado);

    }
}
