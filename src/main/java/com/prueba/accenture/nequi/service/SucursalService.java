package com.prueba.accenture.nequi.service;

import com.prueba.accenture.nequi.exceptions.BadRequestException;
import com.prueba.accenture.nequi.model.Sucursal;
import com.prueba.accenture.nequi.repository.FranquiciaRepository;
import com.prueba.accenture.nequi.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SucursalService {

    private SucursalRepository sucursalRepository;
    private FranquiciaRepository franquiciaRepository;

    public SucursalService(SucursalRepository sucursalRepository, FranquiciaRepository franquiciaRepository) {
        this.sucursalRepository = sucursalRepository;
        this.franquiciaRepository = franquiciaRepository;
    }

    public Mono<Sucursal> addSucursal(Sucursal sucursal) {
        return franquiciaRepository.findByIdFranquicia(sucursal.getId_franquicia())
                .switchIfEmpty(Mono.error(new BadRequestException("400","FRANQUICIA NO ENCONTRADA","REVISAR LA FRANQUICIA","MSG-001",400)))
                .onErrorResume(error->{
                    log.error("ERRO EN EL PROCESO"+ error);
                    return Mono.error(new BadRequestException("400","ERROR PROCESANDO LA CONSULTA","REVISAR LA ESTRUCTURA DE LA CONSULTA","MSG-002",400));})
                .flatMap(franquicia->{
                    log.info("Encontro la Franquicia");
                    return sucursalRepository.save(sucursal);
                });
    }

    public Mono<Sucursal> updateSucursalName(Sucursal sucursal) {
        return sucursalRepository.findByIdSucursal(sucursal.getId_sucursal())
                .switchIfEmpty(Mono.error(new BadRequestException("400","SUCURSAL NO ENCONTRADA","REVISAR EL ID DE LA SUCURSAL","MSG-001",400)))
                .onErrorResume(error->{
                    log.error("ERRO EN EL PROCESO"+ error);
                    return Mono.error(new BadRequestException("400","ERROR PROCESANDO LA CONSULTA","REVISAR LA ESTRUCTURA DE LA CONSULTA","MSG-002",400));})
                .flatMap(sucursalData->{
                    log.info("Encontro la Franquicia");
                    return sucursalRepository.updateSucursalName(sucursal.getId_sucursal(), sucursal.getName()).switchIfEmpty(Mono.just(sucursalData));
                });
    }
}
