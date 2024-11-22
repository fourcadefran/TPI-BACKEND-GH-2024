package fran.fourcade.pruebasmicroservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "notificacion")
//    @JsonBackReference
    private Prueba prueba;

    @Column(nullable = false)
    private String mensaje;


    public Notificacion(Prueba prueba, String mensaje) {
        this.prueba = prueba;
        this.mensaje = mensaje;
    }
}
