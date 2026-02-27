package com.inube.facturas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

//@Data es una anotación de Lombok que genera automáticamente;
//  - Getters y Setters
//  - toString()
//  - equals() y hashCode()
//  - Además, evita escribir todo ese codigo manualmente.
@Data

// Genera un constructor vacío (sin parámetros)
@NoArgsConstructor

// Genera un constructor con todos los parámetros
@AllArgsConstructor

// @Builder permite crear objetos usando el patron Builder
@Builder

// Indica que esta clase es una entidad JPA y se mapeará a una tabla de BD
@Entity

// Especifica el nombre de la tabla a la que se mapeara en la base de datos
@Table(name = "CLIENTES") //MUY IMPORTANTE para evitar errores de traducción de Java

public class Cliente {

    // Indica que este atributo es la llave primaria
    @Id

    // Configuración para que el ID se genere mediante una secuencia (Oracle)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")

    //Define la secuencia de Oracle que usara JPA para generar los IDs
    @SequenceGenerator(
            name = "cliente_seq",
            sequenceName = "SEC_ID_CLIENTE",
            allocationSize = 1
    )

    //Mapea el atributo con la columna ID_CLIENTE (NOT NULL)
    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente; //NUMBER en Oracle -> Long en Java

    // Mapea la columna NOMBRE, obligatoria (NOT NULL) con longitud maxima 40
    @Column(name = "NOMBRE", nullable = false, length = 40)
    private String nombre;

    // Mapea APATERNO, obligatorio
    @Column(name = "APATERNO", nullable = false, length = 40)
    private String apellidoPaterno;

    // Mapea AMATERNO, este es opcional porque NO tiene nullable=false
    @Column(name = "AMATERNO")
    private String apellidoMaterno;

    // Campo RFC opcional con longitud maxima de 13 caracteres
    @Column(name = "RFC", length = 13)
    private String rfc;

    // Mapa la columna FECHA_ALTA (DATE) y asigna la fecha actual por defecto
    @Column(name = "FECHA_ALTA")
    private LocalDate fechaAlta = LocalDate.now();

    // Campo ACTIVO obligatorio. 1 = activo, 0 = inactivo
    @Column(name = "ACTIVO", nullable = false)
    private Integer activo = 1;

    //Relación Uno a Muchos con la entidad Teléfono:
    // - mappedBy = "cliente": indica que la FK está en la tabla TELÉFONOS.
    // - cascade = ALL: al guardar o borrar un cliente, sus teléfonos también.
    // - fetch = LAZY: solo carga los teléfonos cuando se necesitan
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Telefono> telefonos;
}
