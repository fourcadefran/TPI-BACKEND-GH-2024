package fran.fourcade.pruebasmicroservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "vehiculo")
    private List<Prueba> pruebas;

    @OneToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    public void setPruebas(Prueba prueba) {
        this.pruebas.add(prueba);
    }


}
