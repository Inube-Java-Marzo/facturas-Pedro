package com.inube.facturas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

//Estas etiquetas nos están dando los comportamientos
// de la clase a traves de constructores
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAGOS")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pago_seq")
    @SequenceGenerator(name = "pago_seq", sequenceName = "SEC_ID_PAGO", allocationSize = 1)
    @Column(name = "ID_PAGO", nullable = false)
    private Long idPago;

    //Muchos pagos pueden ir a una sola factura (Relacion con FACTURA)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_FACTURA", nullable = false,
    referencedColumnName = "ID_FACTURA",
    foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_FACTURA_PAGOS"))
    private Factura factura;

    @Column(name = "MONTO", nullable = false, length = 10)
    private Double monto;

    @Column(name = "FECHA_PAGO", nullable = false)
    private LocalDate fechaPago;

}
