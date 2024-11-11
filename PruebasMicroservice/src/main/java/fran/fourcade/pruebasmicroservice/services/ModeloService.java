package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Marca;
import fran.fourcade.pruebasmicroservice.models.Modelo;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.MarcaRepository;
import fran.fourcade.pruebasmicroservice.repositories.ModeloRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public ModeloService(ModeloRepository modeloRepository, MarcaRepository marcaRepository, VehiculoRepository vehiculoRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public Iterable<Modelo> getAll() {
        return modeloRepository.findAll();
    }
    public Modelo getById(Long id) throws ServiceExceptionPrueba  {
        return modeloRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("El modelo no existe"));
    }

    public Modelo create(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public void delete(Long id) {
        modeloRepository.deleteById(id);
    }
    public Modelo update(Long id, Modelo modeloDetails) throws ServiceExceptionPrueba {
        Marca marca = marcaRepository.findById(modeloDetails.getMarca().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("El modelo no existe"));

        Vehiculo vehiculo = vehiculoRepository.findById(modeloDetails.getVehiculo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("El vehiculo no existe"));

        Modelo modelo = modeloRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("El modelo no existe"));

        modelo.setMarca(marca);
        modelo.setVehiculo(vehiculo);
        modelo.setDescripcion(modeloDetails.getDescripcion());

        return modeloRepository.save(modelo);
    }

}
