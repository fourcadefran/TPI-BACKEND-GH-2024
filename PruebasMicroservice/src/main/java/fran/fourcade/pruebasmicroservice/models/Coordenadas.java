package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Coordenadas {
    // Getters y setters
    private double lat;
    private double lon;

}
