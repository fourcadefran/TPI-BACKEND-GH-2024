package fran.fourcade.pruebasmicroservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PosicionDTO {
    private VehiculoDTO vehiculo;
    private String fechaHora;
    private int latitud;
    private int longitud;
}
