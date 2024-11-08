package com.prueba.accenture.nequi.repository;

import com.prueba.accenture.nequi.model.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends R2dbcRepository<Producto, Integer> {

    @Query("SELECT * FROM producto WHERE id_sucursal=:sucursal")
    Flux<Producto> findBySucursa(Integer sucursal);

    @Query("SELECT * FROM producto WHERE id_producto=:idProducto")
    Mono<Producto> findByProducto(Integer idProducto);

    @Query("SELECT * FROM producto WHERE id_producto=:idProducto AND id_sucursal=:sucursal")
    Mono<Producto> findByProductoAndIdSucursal(Integer idProducto, Integer sucursal);

    @Query("DELETE FROM producto WHERE id_producto=:idProducto AND id_sucursal=:sucursal")
    Mono<Producto> deleteProductoBySucursal(Integer sucursal, Integer idProducto);

    @Query("UPDATE producto SET stock =:stock  WHERE id_producto=:idProducto AND id_sucursal=:sucursal")
    Mono<Producto> updateProductoBySucursal(Integer sucursal, Integer idProducto, Integer stock);

    @Query("UPDATE producto SET name =:productName  WHERE id_producto=:idProducto")
    Mono<Producto> updateProductName(Integer idProducto, String productName);
}
