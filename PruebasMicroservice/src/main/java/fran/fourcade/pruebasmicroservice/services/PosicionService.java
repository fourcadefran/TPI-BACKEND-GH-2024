package fran.fourcade.pruebasmicroservice.services;

import fran.fourcade.pruebasmicroservice.dtos.PosicionDTO;
import fran.fourcade.pruebasmicroservice.models.Posicion;
import fran.fourcade.pruebasmicroservice.models.Vehiculo;
import fran.fourcade.pruebasmicroservice.repositories.PosicionRepository;
import fran.fourcade.pruebasmicroservice.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosicionService {
    private final PosicionRepository posicionRepository;
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public PosicionService(PosicionRepository posicionRepository, VehiculoRepository vehiculoRepository) {
        this.posicionRepository = posicionRepository;
        this.vehiculoRepository = vehiculoRepository;
    }
    public static Posicion convertirDTOAEntidad(PosicionDTO posicionDTO) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(posicionDTO.getVehiculo().getId());
        Posicion posicion = new Posicion();
        posicion.setVehiculo(vehiculo);
        posicion.setLatitud(posicionDTO.getLatitud());
        posicion.setLongitud(posicionDTO.getLongitud());
        posicion.setFechaHora(posicionDTO.getFechaHora());
        return posicion;
    }
    public Iterable<Posicion> getAll() {
        return posicionRepository.findAll();
    }
    public Posicion getById(Long id) throws ServiceExceptionPrueba {
        return posicionRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("no se encoentro la posicion"));
    }

    public Posicion create(Posicion posicion) {
        return posicionRepository.save(posicion);
    }

    public void delete(Long id) {
        posicionRepository.deleteById(id);
    }

    public Posicion update(Long id, Posicion posicionDetails) throws ServiceExceptionPrueba {
        Posicion posicion = posicionRepository.findById(id).orElseThrow(() -> new ServiceExceptionPrueba("no se encotro la posicion"));
        posicion.setFechaHora(posicionDetails.getFechaHora());
        posicion.setLatitud(posicionDetails.getLatitud());
        posicion.setLongitud(posicionDetails.getLongitud());

        Vehiculo vehiculo = vehiculoRepository.findById(posicionDetails.getVehiculo().getId())
                .orElseThrow(() -> new ServiceExceptionPrueba("no se encotro la vehiculo"));

        posicion.setVehiculo(vehiculo);
        return posicionRepository.save(posicion);
    }
}
