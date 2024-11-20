package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity

@Setter
@Getter
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coordenadas coordenadasAgencia;

    @Column
    private int radioAdmitidoKm;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agencia_id")
    private List<ZonaRestringida> zonasRestringidas;

}
