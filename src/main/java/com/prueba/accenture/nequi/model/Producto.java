package com.prueba.accenture.nequi.model;


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
    private String name;
    private Integer stock;

    @Column("id_sucursal")
    private Integer id_sucursal;
}
