package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Marca;
import fran.fourcade.pruebasmicroservice.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;

    @Autowired
    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Iterable<Marca> getAll() {
        return marcaRepository.findAll();
    }
    public Marca getById(Long id) throws ServiceExceptionPrueba {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el marca"));
    }
    public Marca create(Marca marca) {
        return marcaRepository.save(marca);
    }

    public void delete(Long id) {
        marcaRepository.deleteById(id);
    }

    public Marca update(Long id, Marca marcaDetails) throws ServiceExceptionPrueba {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el marca"));
        marca.setNombre(marcaDetails.getNombre());
        return marcaRepository.save(marca);
    }
}
