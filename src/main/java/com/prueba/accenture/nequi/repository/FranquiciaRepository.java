package com.prueba.accenture.nequi.repository;

import com.prueba.accenture.nequi.model.Franquicia;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository extends R2dbcRepository<Franquicia, Integer> {

    @Query("SELECT * FROM franquicia WHERE id_franquicia=:idFranquicia")
    Mono<Franquicia> findByIdFranquicia(Integer idFranquicia);

    @Query("UPDATE franquicia set name=:newName WHERE id_franquicia=:idFranquicia")
    Mono<Franquicia> updateNombreFranquicia(Integer idFranquicia, String newName);


}
