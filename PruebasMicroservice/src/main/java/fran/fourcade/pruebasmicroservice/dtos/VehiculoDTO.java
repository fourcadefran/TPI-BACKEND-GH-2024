package fran.fourcade.pruebasmicroservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehiculoDTO {
    private Long id;
    private String patente;
    private ModeloDTO modelo;

    // Getters and Setters
}
