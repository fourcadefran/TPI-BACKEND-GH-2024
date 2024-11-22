package fran.fourcade.pruebasmicroservice.dtos;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PruebaDTO {
    private VehiculoDTO vehiculo;
    private InteresadoDTO interesado;
    private int idEmpleado;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String comentarios;

    // Getters and Setters
}
