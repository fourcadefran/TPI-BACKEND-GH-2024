package fran.fourcade.pruebasmicroservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModeloDTO {
    private Long id;
    private String descripcion;
    private MarcaDTO marca;

    // Getters and Setters
}
