package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.*;


@Entity

public class ZonaRestringida {
    // Getters y setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coordenadas noroeste;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "sureste_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "sureste_lon"))
    })
    private Coordenadas sureste;

    public Coordenadas getNoroeste() {
        return noroeste;
    }

    public void setNoroeste(Coordenadas noroeste) {
        this.noroeste = noroeste;
    }

    public Coordenadas getSureste() {
        return sureste;
    }

    public void setSureste(Coordenadas sureste) {
        this.sureste = sureste;
    }
}