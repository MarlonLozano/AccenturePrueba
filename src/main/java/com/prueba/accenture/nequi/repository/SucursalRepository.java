package com.prueba.accenture.nequi.repository;

import com.prueba.accenture.nequi.model.Sucursal;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository extends R2dbcRepository<Sucursal, Integer> {

    @Query("SELECT * FROM sucursal WHERE id_franquicia=:franquicia")
    Flux<Sucursal> findByIdFranquicia(Integer franquicia);

    @Query("SELECT * FROM sucursal WHERE id_sucursal=:idSucursal")
    Mono<Sucursal> findByIdSucursal(Integer idSucursal);

    @Query("UPDATE sucursal SET name = :name WHERE id_sucursal=:idSucursal")
    Mono<Sucursal> updateSucursalName(Integer idSucursal, String name);
}
