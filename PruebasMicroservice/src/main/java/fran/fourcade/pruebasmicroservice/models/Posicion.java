package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "posiciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private String fechaHora;

    @Column(nullable = false)
    private int latitud;

    @Column(nullable = false)
    private int longitud;

}
