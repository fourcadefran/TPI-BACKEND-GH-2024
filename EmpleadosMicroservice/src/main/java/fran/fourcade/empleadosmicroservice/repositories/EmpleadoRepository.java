package fran.fourcade.empleadosmicroservice.repositories;

import fran.fourcade.empleadosmicroservice.models.Empleado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpleadoRepository extends CrudRepository<Empleado, Long> {
    //metodos crud basics

    List<Empleado> findByLegajo(String legajo);
    List<Empleado> findByNombre(String nombre);
    List<Empleado> findByApellido(String apellido);
    List<Empleado> findByTelefonoContacto(int telefonoContacto);
}
