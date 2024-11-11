package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "modelos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @OneToOne(mappedBy = "modelo")
    private Vehiculo vehiculo;

    @OneToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
}
