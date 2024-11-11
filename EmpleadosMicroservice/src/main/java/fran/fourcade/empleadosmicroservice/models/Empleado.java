package fran.fourcade.empleadosmicroservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "empleados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    //todo: check @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "legajo", unique = true)
    private String legajo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "telefono_contacto", nullable = false)
    private int telefonoContacto;

}
