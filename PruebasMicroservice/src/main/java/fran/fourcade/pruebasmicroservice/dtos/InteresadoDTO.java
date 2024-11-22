package fran.fourcade.pruebasmicroservice.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InteresadoDTO {
    private Long id;
    private String tipoDocumento;
    private String documento;
    private String nombre;
    private String apellido;
    private Boolean restringido;
    private Long nroLicencia;
    private LocalDate fechaVencimientoLicencia;

    // Getters and Setters
}
