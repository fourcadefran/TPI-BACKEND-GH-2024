package fran.fourcade.pruebasmicroservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "vehiculos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patente;

    @OneToOne(mappedBy = "vehiculo")
    @JsonBackReference
    private Prueba prueba;

    @OneToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;


}
