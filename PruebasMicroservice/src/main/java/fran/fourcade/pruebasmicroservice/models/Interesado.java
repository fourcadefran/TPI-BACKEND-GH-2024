package fran.fourcade.pruebasmicroservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "interesados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interesado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Boolean restringido;

    @Column(nullable = false)
    private int nroLicencia;

    @Column(nullable = false)
    private String fechaVencimientoLicencia;

    @OneToOne(mappedBy = "interesado")
    private Prueba prueba;

}
