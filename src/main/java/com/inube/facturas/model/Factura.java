package com.inube.facturas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;


//Iniciar con los decoradores, los imports se hacen solos
//Apoyarse del autocompletado
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FACTURAS")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura-seq")
    @SequenceGenerator(name = "factura_seq", sequenceName = "SEC_ID_FACTURA", allocationSize = 1)
    @Column(name = "ID_FACTURA", nullable = false)
    private Long idFactura;

    //Para cardinalidad, tomar de referencia la clase FACTURAS (Muchas para un solo cliente)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_CLIENTE", nullable = false,
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_CLIENTE_FACTURAS"))
    private Cliente cliente;

    @Column(name = "MONTO", nullable = false, length = 10)
    private Double monto; //Recomendable para precios

    @Column(name = "FOLIO", nullable = false)
    private String folio;

    @Column(name = "ANIO", nullable = false)
    private String anio;

    @Column(name = "FECHA_FACTURA", nullable = false)
    private LocalDate fechaFactura;

    //Crearlo inmediatemente despues de agregar el campo en la BD
    @Column(name = "ACTIVO", nullable = false)
    private Integer activo = 1;

    //Modelando lo que quiero ver en mi clase
    //Una factura puede tener varios pagos, entonces...
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;



}
