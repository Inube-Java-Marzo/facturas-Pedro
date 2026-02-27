package com.inube.facturas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Incluye @Getter, @Setter, @ToString, @EqualsAndHashCode, y @RequiredArgsContructor
@NoArgsConstructor // Genera un constructor sin argumentos (Sobrecarga de metodos)
@AllArgsConstructor //Genera un constructor con todos los argumentos
@Entity
@Table(name = "TELEFONOS")

//@ManyToOne Clase perteneciente a clase referenciada

public class Telefono {
    @Id
    // Configuracion para bases de datos Oracle (secuencia)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefono_seq")
    @SequenceGenerator(name = "telefono_seq", sequenceName = "SEC_ID_TELEFONO", allocationSize = 1)
    @Column(name = "ID_TELEFONO", nullable = false)
    private Long idTelefono; //Usamos Long para el ID de tipo NUMBER

    // Relación con la tabla CLIENTES
    // Muchos teléfonos pueden pertenecer a un cliente (ManyToOne)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_CLIENTE", nullable = false,
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_CLIENTE_TELEFONOS"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente; // Asume que tienes una entidad Cliente

    @Column(name = "TELEFONO", nullable = false, length = 15)
    private String telefono; // Varchar(15) se mapea a String
}
