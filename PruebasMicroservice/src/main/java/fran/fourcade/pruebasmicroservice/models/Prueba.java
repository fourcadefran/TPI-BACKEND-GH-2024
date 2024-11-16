package fran.fourcade.pruebasmicroservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// todo: notificaciones

@Entity(name = "pruebas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_vehiculo")
    @JsonManagedReference //todo check relacion circular infinita
    private Vehiculo vehiculo;

    @OneToOne
    @JoinColumn(name = "id_interesado")
    private Interesado interesado;

    //private Interesado interesado;

    @Column(nullable = false)
    private int idEmpleado;

    //private Empleado empleado

    @Column(nullable = false)
    private String fechaHoraInicio;

    @Column()
    private String fechaHoraFin;

    @Column(nullable = false)
    private String comentarios;

    @Column
    private boolean excedeLimites;

    /**
     * Finaliza la prueba con los comentarios proporcionados y marca la fecha de finalización.
     *
     * @param comentarios Comentarios finales para la prueba.
     */
    public void finalizar(String comentarios) {
        this.comentarios = comentarios;
        this.fechaHoraFin = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Marca que la prueba excedió los límites.
     */
    public void marcarExcesoDeLimites() {
        this.excedeLimites = true;
    }
}
