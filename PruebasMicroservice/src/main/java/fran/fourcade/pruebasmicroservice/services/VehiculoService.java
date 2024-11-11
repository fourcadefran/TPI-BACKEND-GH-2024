package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Modelo;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.ModeloRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final PruebaRepository pruebaRepository;
    private final ModeloRepository modeloRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, PruebaRepository pruebaRepository, ModeloRepository modeloRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.pruebaRepository = pruebaRepository;
        this.modeloRepository = modeloRepository;
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
}
