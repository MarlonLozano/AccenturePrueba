package com.prueba.accenture.nequi.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("producto")
public class Producto {

    private Integer id_producto;

    @NotNull(message = "El campo 'name' no puede estar vacio")
    @Size(min = 1, max = 20, message = "El campo 'name' debe tener entre 1 y 20 caracteres")
    private String name;

    @NotNull(message = "El campo 'stock' no puede estar vacio")
    @Size(min = 1, message = "El campo 'stock' debe ser minimo 1")
    private Integer stock;

    @Column("id_sucursal")
    private Integer id_sucursal;
}
