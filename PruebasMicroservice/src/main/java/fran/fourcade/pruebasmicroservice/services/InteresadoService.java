package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.models.Interesado;
import fran.fourcade.pruebasmicroservice.models.Prueba;
import fran.fourcade.pruebasmicroservice.repositories.InteresadoRepository;
import fran.fourcade.pruebasmicroservice.repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresadoService {
    private final InteresadoRepository interesadoRepository;
    private final PruebaRepository pruebaRepository;

    @Autowired
    public InteresadoService(InteresadoRepository interesadoRepository, PruebaRepository pruebaRepository) {
        this.interesadoRepository = interesadoRepository;
        this.pruebaRepository = pruebaRepository;
    }

    public Iterable<Interesado> getAll() {
        return interesadoRepository.findAll();
    }

    public Interesado getById(Long id) throws ServiceExceptionPrueba {
        return interesadoRepository
                .findById(id)
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontro el interesado"));
    }

    public Interesado create(Interesado interesado) {
        return interesadoRepository.save(interesado);
    }
    public void delete(Long id) {
        interesadoRepository.deleteById(id);
    }
    public Interesado update(Long id, Interesado interesadoDetails) throws ServiceExceptionPrueba {
        Interesado interesado = interesadoRepository.findById(id).orElseThrow(() -> new SecurityException("No se encontro el interesado"));

        interesado.setNombre(interesadoDetails.getNombre());
        interesado.setApellido(interesadoDetails.getApellido());
        interesado.setDocumento(interesadoDetails.getDocumento());
        interesado.setRestringido(interesadoDetails.getRestringido());
        interesado.setTipoDocumento(interesadoDetails.getTipoDocumento());
        interesado.setNroLicencia(interesadoDetails.getNroLicencia());
        interesado.setFechaVencimientoLicencia(interesadoDetails.getFechaVencimientoLicencia());

        Prueba prueba = pruebaRepository.findById(interesadoDetails.getPrueba().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("No se encontró la prueba"));
        interesado.setPrueba(prueba);

        return interesadoRepository.save(interesado);
    }
}
