package com.prueba.accenture.nequi.model;

import lombok.Data;

@Data
public class ProductoSucursal {

    private final Producto producto;
    private final Sucursal sucursal;

    public ProductoSucursal(Producto producto, Sucursal sucursal) {
        this.producto = producto;
        this.sucursal = sucursal;
    }
}
